package com.cx.monitor.service.impl;

import com.cx.common.exception.RedisConnectException;
import com.cx.common.function.JedisExecutor;
import com.cx.monitor.entity.RedisInfo;
import com.cx.monitor.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * Redis 工具类，只封装了几个常用的 redis 命令，
 * 可根据实际需要按类似的方式扩展即可。
 *
 * @author Administrator·
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "spring.redis", name = "redisFlag", havingValue = "true", matchIfMissing = true)
public class RedisServiceImpl implements IRedisService {

    private static String separator = System.getProperty("line.separator");

    @Autowired
    JedisPool jedisPool;

    /**
     * 处理 jedis请求
     *
     * @param j 处理逻辑，通过 lambda行为参数化
     * @return 处理结果
     */
    private <T> T excuteByJedis(JedisExecutor<Jedis, T> j) throws RedisConnectException {
        try (Jedis jedis = jedisPool.getResource()) {
            return j.excute(jedis);
        } catch (Exception e) {
            throw new RedisConnectException("连接失败");
        }
    }

    @Override
    public List<RedisInfo> getRedisInfo() {
        try {
            String info = this.excuteByJedis(
                    j -> {
                        Client client = j.getClient();
                        client.info();
                        return client.getBulkReply();
                    }
            );
            List<RedisInfo> infoList = new ArrayList<>();
            String[] strs = Objects.requireNonNull(info).split(separator);
            RedisInfo redisInfo;
            if (strs.length > 0) {
                for (String str1 : strs) {
                    redisInfo = new RedisInfo();
                    String[] str = str1.split(":");
                    if (str.length > 1) {
                        String key = str[0];
                        String value = str[1];
                        redisInfo.setKey(key);
                        redisInfo.setValue(value);
                        infoList.add(redisInfo);
                    }
                }
            }
            return infoList;
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Map<String, Object> getKeysSize() {
        try {
            Long dbSize = this.excuteByJedis(
                    j -> {
                        Client client = j.getClient();
                        client.dbSize();
                        return client.getIntegerReply();
                    }
            );
            Map<String, Object> map = new HashMap<>(16);
            map.put("dbSize", dbSize);
            return map;
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Map<String, Object> getMemoryInfo() {
        try {
            String info = this.excuteByJedis(
                    j -> {
                        Client client = j.getClient();
                        client.info();
                        return client.getBulkReply();
                    }
            );
            String[] strs = Objects.requireNonNull(info).split(separator);
            Map<String, Object> map = null;
            for (String s : strs) {
                String[] detail = s.split(":");
                if ("used_memory".equals(detail[0])) {
                    map = new HashMap<>(16);
                    map.put("used_memory", detail[1].substring(0, detail[1].length() - 1));
                    break;
                }
            }
            return map;
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Set<String> getKeys(String pattern) {
        try {
            return this.excuteByJedis(j -> j.keys(pattern));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public String get(String key) {
        try {
            return this.excuteByJedis(j -> j.get(key.toLowerCase()));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public String set(String key, String value) {
        try {
            return this.excuteByJedis(j -> j.set(key.toLowerCase(), value));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public String set(String key, String value, Long milliscends) {
        try {
            String result = this.set(key.toLowerCase(), value);
            this.pexpire(key.toLowerCase(), milliscends);
            return result;
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Long del(String... key) {
        try {
            return this.excuteByJedis(j -> j.del(key));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Boolean exists(String key) {
        try {
            return this.excuteByJedis(j -> j.exists(key));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Long pttl(String key) {
        try {
            return this.excuteByJedis(j -> j.pttl(key));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Long pexpire(String key, Long milliseconds) {
        try {
            return this.excuteByJedis(j -> j.pexpire(key, milliseconds));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Long zadd(String key, Double score, String member) {
        try {
            return this.excuteByJedis(j -> j.zadd(key, score, member));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        try {
            return this.excuteByJedis(j -> j.zrangeByScore(key, min, max));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        try {
            return this.excuteByJedis(j -> j.zremrangeByScore(key, start, end));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @Override
    public Long zrem(String key, String... members) {
        try {
            return this.excuteByJedis(j -> j.zrem(key, members));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

}
