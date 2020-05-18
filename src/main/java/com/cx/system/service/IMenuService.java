package com.cx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cx.common.entity.MenuTree;
import com.cx.system.entity.Menu;

import java.util.List;

/**
 * @author Administrator·
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 查找用户权限集
     *
     * @param username 用户名
     * @return 用户权限集合
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 查找用户菜单集合
     *
     * @param username 用户名
     * @return 用户菜单集合
     */
    MenuTree<Menu> findUserMenus(String username);

    /**
     * 查找所有的菜单/按钮 （树形结构）
     *
     * @param menu
     * @return MenuTree<Menu>
     */
    MenuTree<Menu> findMenus(Menu menu);

    /**
     * 查找所有的菜单/按钮
     *
     * @param menu
     * @return MenuTree<Menu>
     */
    List<Menu> findMenuList(Menu menu);

    /**
     * 新增菜单（按钮）
     *
     * @param menu 菜单（按钮）对象
     */
    void createMenu(Menu menu);

    /**
     * 修改菜单（按钮）
     *
     * @param menu 菜单（按钮）对象
     */
    void updateMenu(Menu menu);

    /**
     * 删除菜单（按钮）
     *
     * @param menuIds 菜单（按钮）id
     */
    void deleteMeuns(String menuIds);

    /**
     * 查询所有菜单
     * @return
     */
    MenuTree<Menu> findAllMenus();

    /**
     * 根据权限名称获取按钮权限
     * @param roleName
     * @return
     */
    List<String> findBtnPermissions(String roleName);
}
