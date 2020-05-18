package com.cx.common.authentication.session;


import com.cx.monitor.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听session创建对象
 */
@Component
@Slf4j
class HttpSessionCreatedEventListener implements ApplicationListener<HttpSessionCreatedEvent> {

    @Autowired
    IRedisService redisService;

    @Override
    public void onApplicationEvent(HttpSessionCreatedEvent event) {
        log.info("新建session:{}", event.getSession().getId());
        try {
            // 保存 session
            SessionContext.getInstance().addSession( event.getSession());
        } catch (Exception e) {
            log.info(String.format("添加session:[%s]出现异常.", event.getSession().getId()), e);
        }
    }
}