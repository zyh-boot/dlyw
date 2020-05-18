package com.cx.common.authentication.session;


import com.cx.common.entity.Constant;
import com.cx.monitor.service.IRedisService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听session失效事件
 */
@Component
@Slf4j
class SessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {
    @Autowired
    IRedisService redisService;

    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        log.info("失效session:{}", event.getSession().getId());
        try {
            // 移除session
            SessionContext.getInstance().delSession(event.getSession());
            Object obj= event.getSecurityContexts().get(0).getAuthentication().getPrincipal();
            if(obj !=null){
                User user=(User) obj;
                redisService.del(Constant.REDIS_LOGIN_SESSION_INFO+ user.getUsername());
            }
        } catch (Exception e) {
            log.error(String.format("失效session:[%s]发生异常.", event.getId()), e);
        }
    }
}