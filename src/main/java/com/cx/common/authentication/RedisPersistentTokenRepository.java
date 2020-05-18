package com.cx.common.authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cx.common.properties.CommonProperties;
import com.cx.monitor.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Administrator
 */
@Slf4j
@Configuration
public class RedisPersistentTokenRepository implements PersistentTokenRepository {

    @Autowired
    IRedisService redisService;
    @Autowired
    CommonProperties commonProperties;


    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        if (log.isDebugEnabled()) {
            log.debug("token create seriesId: [{}]", token.getSeries());
        }
        String key = generateKey(token.getSeries());
        String userKey = generateKey(token.getUsername());
        HashMap<String, String> map = new HashMap();
        map.put("username", token.getUsername());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", String.valueOf(token.getDate().getTime()));
        redisService.set(userKey, key, commonProperties.getSecurity().getCookieTimeout()* 1000L);
        redisService.set(key, JSON.toJSONString(map), commonProperties.getSecurity().getCookieTimeout()* 1000L);

    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String key = generateKey(series);
        String hashValues = redisService.get(key);
        HashMap<String, String> map = JSON.parseObject(hashValues, HashMap.class);
        String userKey = generateKey(map.get("userName"));
        map.put("tokenValue", tokenValue);
        map.put("date", String.valueOf(lastUsed.getTime()));
        redisService.set(userKey, key, commonProperties.getSecurity().getCookieTimeout()* 1000L);
        redisService.set(key, JSON.toJSONString(map), commonProperties.getSecurity().getCookieTimeout()* 1000L);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String key = generateKey(seriesId);
        String hashValues = redisService.get(key);
        if(StringUtils.isBlank(hashValues)){
            return null;
        }
        JSONObject map = JSON.parseObject(hashValues);
        String username = map.getString("username");
        String tokenValue = map.getString("tokenValue");
        String date = map.getString("date");
        if (null == username || null == tokenValue || null == date) {
            return null;
        }
        Long timestamp = Long.valueOf(date);
        Date time = new Date(timestamp);
        PersistentRememberMeToken token = new PersistentRememberMeToken(username, seriesId, tokenValue, time);
        return token;
    }

    @Override
    public void removeUserTokens(String userName) {
        String userKey = generateKey(userName);
        String key = redisService.get(userKey);
        redisService.del(key);
        redisService.del(userKey);
    }

    /**
     * 生成key
     *
     * @param series
     * @return
     */
    private String generateKey(String series) {
        return "spring:security:rememberme:token:" + StringUtils.lowerCase(series);
    }
}
