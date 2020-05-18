package com.cx.common.handler;

import com.cx.common.properties.CommonProperties;
import com.cx.common.properties.CommonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 账户同时只能一个登陆 session失效
 * @author Administrator
 */
@Component
public class MySessionExpiredStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    CommonProperties properties;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "账户已在其他地方登录");
    }
}