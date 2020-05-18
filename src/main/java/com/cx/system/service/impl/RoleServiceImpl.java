package com.cx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.common.authentication.session.SessionContext;
import com.cx.common.entity.Constant;
import com.cx.common.entity.QueryRequest;
import com.cx.common.utils.CommonUtil;
import com.cx.common.utils.SortUtil;
import com.cx.monitor.service.IRedisService;
import com.cx.system.entity.Menu;
import com.cx.system.entity.Role;
import com.cx.system.entity.RoleMenu;
import com.cx.system.entity.User;
import com.cx.system.mapper.RoleMapper;
import com.cx.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Administrator·
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;

    @Override
    public List<Role> findUserRole(String username) {
        return this.baseMapper.findUserRole(username);
    }

    @Override
    public List<Role> findRoles(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(role.getRoleName())) {
            queryWrapper.lambda().like(Role::getRoleName, role.getRoleName());
        }
        if(!CommonUtil.getRootUser()){
            queryWrapper.lambda().ne(Role::getRoleName,Constant.ROOT);
        }
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<Role> findRoles(Role role, QueryRequest request) {
        Page<Role> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", Constant.ORDER_DESC, false);
        return this.baseMapper.findRolePage(page, role);
    }

    @Override
    public Role findByName(String roleName) {
        return this.baseMapper.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName, roleName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(Role role) {
        role.setCreateTime(new Date());
        this.baseMapper.insert(role);
        this.saveRoleMenus(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role) {
        role.setModifyTime(new Date());
        this.updateById(role);
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(String.valueOf(role.getRoleId()));
        this.roleMenuService.deleteRoleMenusByRoleId(roleIdList);
        saveRoleMenus(role);
        refreshSession("");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoles(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(StringPool.COMMA));
        this.baseMapper.delete(new QueryWrapper<Role>().lambda().in(Role::getRoleId, list));

        this.roleMenuService.deleteRoleMenusByRoleId(list);
        this.userRoleService.deleteUserRolesByRoleId(list);
        refreshSession("");
    }

    private void saveRoleMenus(Role role) {
        if (StringUtils.isNotBlank(role.getMenuIds())) {
            String[] menuIds = role.getMenuIds().split(StringPool.COMMA);
            List<RoleMenu> roleMenus = new ArrayList<>();
            Arrays.stream(menuIds).forEach(menuId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(Long.valueOf(menuId));
                roleMenu.setRoleId(role.getRoleId());
                roleMenus.add(roleMenu);
            });
            roleMenuService.saveBatch(roleMenus);
            refreshSession("");
        }
    }

    @Override
    public List<Role> findAllRoles() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询用户权限
     * @return
     */
    @Override
    public List<Role> findUserAllRole(User user) {
        // 获取用户角色集
        List<Role> roleList = new ArrayList<>();
        // 获取用户权限集
        List<Menu> permissionList  = new ArrayList<>();
        if (user.getRoleName().equals(Constant.ROOT)) {
            roleList = findAllRoles();
            permissionList = this.menuService.findUserPermissions("");

        } else {
            roleList = findUserRole(user.getUsername());
            permissionList = this.menuService.findUserPermissions(user.getUsername());
        }
        if(CollectionUtils.isEmpty(roleList)){
            roleList = new ArrayList<>();
        }
        //按钮级权限名称放在menu param
        List<Role> finalRoleList = roleList;
        if(!CollectionUtils.isEmpty(permissionList)){
            permissionList.forEach(menu -> {
                if(StringUtils.isNotBlank(menu.getPerms())){
                    Role  role=new Role();
                    role.setRoleName(menu.getPerms());
                    finalRoleList.add(role);
                }
            });
        }
        return  finalRoleList;
    }

    /**
     * 刷新session
     * 刷新在线用户角色
     */
    @Override
    public void refreshSession(String userName) {
        if(StringUtils.isNotBlank(userName)){
            String sessionId=redisService.get(Constant.REDIS_LOGIN_SESSION_INFO+userName);
            this.reloadUserAuthority(sessionId);
        }else {
            Set<String> sessionIds = SessionContext.getInstance().getSessionId();
            sessionIds.forEach(sessionId -> {
                this.reloadUserAuthority(sessionId);
            });
        }
    }

    /**
     * 重新加载用户的权限
     *
     * @param sessionId
     */
    public void reloadUserAuthority(String sessionId) {
        try {
            HttpSession session= SessionContext.getInstance().getSession(sessionId);
            SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            Authentication authentication = securityContext.getAuthentication();
            Object principal1 = authentication.getPrincipal();
            if (principal1 instanceof User) {
                User user = (User) principal1;
                /**
                 * 重载用户对象
                 */
                user =userService.findUserDetail(user.getUsername());
                user.setRoles(findUserAllRole(user));
                // 重新new一个token，因为Authentication中的权限是不可变的.
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                        user, authentication.getCredentials(),
                        user.getAuthorities());
                result.setDetails(authentication.getDetails());
                securityContext.setAuthentication(result);
            }
        } catch (Exception e) {
            log.error("当前用户session有可能不存在");
        }

    }


}
