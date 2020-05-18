package com.cx.common.authentication.session;


import com.cx.common.entity.Constant;
import com.cx.monitor.service.IRedisService;
import com.cx.system.entity.User;
import com.cx.common.entity.Constant;
import com.cx.monitor.service.IRedisService;
import com.cx.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

/**
 * @author huan.fu
 *         Spring security 登录成功后，防止session的固化攻击，会将旧的sessionId销毁，重新生成一个新的sessionId,
 *         因此此处需要做一下处理
 */
@Component
class SessionFixationProtectionEventListener implements ApplicationListener<SessionFixationProtectionEvent> {
    @Autowired
    IRedisService redisService;
    @Override
    public void onApplicationEvent(SessionFixationProtectionEvent event) {
        String oldSessionId = event.getOldSessionId();
        String newSessionId = event.getNewSessionId();
        Object obj= event.getAuthentication().getPrincipal();
        if(obj !=null){
            User user=(User) obj;
            redisService.set(Constant.REDIS_LOGIN_SESSION_INFO+user.getUsername(), newSessionId,
                    Constant.DAY_SEC * 1000L);
        }
        // 更改sessionId的值
        SessionContext.getInstance().refreshSessionId(oldSessionId,newSessionId);
    }
}
