package com.cx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.system.entity.RoleMenu;

/**
 * @author Administrator·
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    /**
     * 递归删除菜单/按钮
     *
     * @param menuId menuId
     */
    void deleteRoleMenus(String menuId);
}
