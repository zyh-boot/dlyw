package com.cx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator·
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查找用户权限集
     *
     * @param username 用户名
     * @return 用户权限集合
     */
    List<Menu> findUserPermissions(@Param("username") String username);

    /**
     * 查找用户菜单集合
     *
     * @param username 用户名
     * @return 用户菜单集合
     */
    List<Menu> findUserMenus(String username);
    /**
     * 根据权限名称获取按钮权限
     *
     * @param roleNames
     * @return
     */
    List<String> findBtnPermissions(@Param("roleNames") List<String> roleNames);
}
