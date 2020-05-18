package com.cx.common.handler;

import com.cx.common.properties.CommonProperties;
import com.cx.common.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权访问
 *
 * @author Administrator
 */
@Component
public class MyAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    CommonProperties properties;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (CommonUtil.isAjaxRequest(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        } else {
            response.sendRedirect(properties.getSecurity().getLoginUrl());
        }
    }
}
