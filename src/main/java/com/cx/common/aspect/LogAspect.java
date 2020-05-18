package com.cx.common.aspect;

import com.cx.common.properties.CommonProperties;
import com.cx.common.utils.HttpContextUtil;
import com.cx.common.utils.IpUtil;
import com.cx.common.utils.SecurityUtils;
import com.cx.monitor.entity.Log;
import com.cx.monitor.service.ILogService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author Administrator·
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.cx.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 设置 IP地址
        String ip = IpUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (commonProperties.isOpenAopLog()) {
            // 保存日志
            User user = (User) SecurityUtils.getUser();
            Log log = new Log();
            if (user != null) {
                log.setUsername(user.getUsername());
            }
            log.setIp(ip);
            log.setTime(time);
            logService.saveLog(point, log);
        }
        return result;
    }
}
