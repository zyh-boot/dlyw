package com.cx.system.controller;


import com.cx.common.annotation.Log;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.MenuTree;
import com.cx.common.entity.CommonResponse;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.CommonUtil;
import com.cx.system.entity.Menu;
import com.cx.system.entity.User;
import com.cx.system.service.IMenuService;
import com.cx.common.annotation.Log;
import com.cx.common.controller.BaseController;
import com.cx.common.utils.CommonUtil;
import com.cx.system.entity.Menu;
import com.cx.system.entity.User;
import com.cx.system.service.IMenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator·
 */
@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("{username}")
    public CommonResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws CommonException {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername())) {
            throw new CommonException("您无权获取别人的菜单");
        }
        MenuTree<Menu> userMenus = null;
        if (CommonUtil.getRootUser()) {
             userMenus = this.menuService.findAllMenus();
        }else {
            userMenus = this.menuService.findUserMenus(username);
        }
        return new CommonResponse().data(userMenus);
    }

    @GetMapping("tree")
    public CommonResponse getMenuTree(Menu menu) throws CommonException {
        try {
            MenuTree<Menu> menus = this.menuService.findMenus(menu);
            return new CommonResponse().success().data(menus.getChilds());
        } catch (Exception e) {
            String message = "获取菜单树失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("新增菜单/按钮")
    @PostMapping
    @PreAuthorize("hasRole('menu:add')")
    public CommonResponse addMenu(@Valid Menu menu) throws CommonException {
        try {
            this.menuService.createMenu(menu);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "新增菜单/按钮失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("删除菜单/按钮")
    @GetMapping("delete/{menuIds}")
    @PreAuthorize("hasRole('menu:delete')")
    public CommonResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) throws CommonException {
        try {
            this.menuService.deleteMeuns(menuIds);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除菜单/按钮失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("修改菜单/按钮")
    @PostMapping("update")
    @PreAuthorize("hasRole('menu:update')")
    public CommonResponse updateMenu(@Valid Menu menu) throws CommonException {
        try {
            this.menuService.updateMenu(menu);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改菜单/按钮失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @GetMapping("excel")
    @PreAuthorize("hasRole('menu:export')")
    public void export(Menu menu, HttpServletResponse response) throws CommonException {
        try {
            List<Menu> menus = this.menuService.findMenuList(menu);
            ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }
}
