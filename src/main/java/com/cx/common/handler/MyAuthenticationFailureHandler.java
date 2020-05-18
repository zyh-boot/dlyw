package com.cx.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String msg="登陆失败";
        if(exception instanceof UsernameNotFoundException ){
            msg="帐号不存在，请重新输入！";
        }else if(exception instanceof BadCredentialsException ){
            msg="密码错误，请重新输入！";
        }else if(exception instanceof BadCredentialsException ){
            msg="密码错误，请重新输入！";
        }else if(exception instanceof AccountExpiredException ){
            msg="账户过期！";
        }else if(exception instanceof DisabledException ){
            msg="账户不可用！";
        }else if(exception instanceof CredentialsExpiredException ){
            msg="证书过期！";
        }else if(exception instanceof AccountExpiredException ){
            msg="用户状态异常！";
        }

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(mapper.writeValueAsString(exception.getMessage()));
        response.sendRedirect("/login");
    }
}
