package com.cx.common.utils;


import com.cx.monitor.service.IRedisService;
import com.cx.system.entity.Role;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class SecurityUtils {

    private static IRedisService redisService;

    @Autowired
    public SecurityUtils(IRedisService redisService) {
        SecurityUtils.redisService = redisService;
    }


    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Collection<? extends GrantedAuthority> getAllPermission() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }


    /**
     * 重新加载用户的权限
     *
     * @param roles
     */
    public static void reloadUserAuthority(List<Role> roles) {

        Authentication authentication  =SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        principal.setRoles(roles);

        // 重新new一个token，因为Authentication中的权限是不可变的.
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(),
                principal.getAuthorities());
        result.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(result);
    }


    public static boolean hasPermission(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = false;
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals(permission)) {
                hasPermission = true;
            }
        }
        return hasPermission;
    }


    public static User getUser() {
        try {
             //return SystemUser.getUser();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return  (User) authentication.getPrincipal();
        }catch (Exception e){
            log.error("用户获取为空");
        }
        return null;
    }


    public static void logout() {
        try{
            SecurityContextHolder.clearContext();
        }catch (Exception e){
            log.error("{}",e);
        }
    }
}