package com.cx.common.authentication;

import com.alibaba.fastjson.JSON;
import com.cx.common.entity.Constant;
import com.cx.system.entity.Menu;
import com.cx.system.entity.Role;
import com.cx.system.entity.User;
import com.cx.system.service.IMenuService;
import com.cx.system.service.IRoleService;
import com.cx.system.service.IUserService;
import com.cx.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Configuration
public class SecurityUserDetailsService  implements UserDetailsService {

    @Autowired
    IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user =userService.findUserDetail(userName);
        if (user == null) {
            throw new BadCredentialsException("帐号不存在，请重新输入！");
        }
        if (User.STATUS_LOCK.equals(user.getStatus())) {
            throw new LockedException("账号已被锁定,请联系管理员！");
        }
        user.setUserInfo(JSON.toJSON(user));
        user.setRoles(roleService.findUserAllRole(user));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(user.getStatus().equals(Constant.STATE_1+"")?true:false);
        return  user;
    }
}
