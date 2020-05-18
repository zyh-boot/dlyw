package com.cx.system.controller;

import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.common.utils.DateUtil;
import com.cx.common.utils.SecurityUtils;
import com.cx.system.entity.User;
import com.cx.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator·
 */
@Controller("systemView")
public class ViewController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(CommonUtil.view("login"));
        return mav;
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return CommonUtil.view("error/403");
    }

    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }


    @RequestMapping("index")
    public String index(Model model) {
        User user = super.getCurrentUser();
        user.setPassword("It's a secret");
        // 获取实时的用户信息
        model.addAttribute("user",super.getCurrentUser());
        model.addAttribute("permissions", SecurityUtils.getAllPermission());
        model.addAttribute("roles", SecurityUtils.getAuthentication());
        return "index";
    }

    @GetMapping(Constant.VIEW_PREFIX + "layout")
    public String layout() {
        return CommonUtil.view("layout");
    }

    @GetMapping(Constant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return CommonUtil.view("system/user/passwordUpdate");
    }

    @GetMapping(Constant.VIEW_PREFIX + "sysUser/profile")
    public String userProfile() {
        return CommonUtil.view("system/user/userProfile");
    }

    @GetMapping(Constant.VIEW_PREFIX + "sysUser/avatar")
    public String userAvatar() {
        return CommonUtil.view("system/user/avatar");
    }

    @GetMapping(Constant.VIEW_PREFIX + "sysUser/profile/update")
    public String profileUpdate() {
        return CommonUtil.view("system/user/profileUpdate");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/user")
    @PreAuthorize("hasRole('user:view')")
    public String systemUser() {
        return CommonUtil.view("system/user/user");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/user/add")
    @PreAuthorize("hasRole('user:add')")
    public String systemUserAdd() {
        return CommonUtil.view("system/user/userAdd");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/user/detail/{username}")
    @PreAuthorize("hasRole('user:view')")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return CommonUtil.view("system/user/userDetail");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/user/update/{username}")
    @PreAuthorize("hasRole('user:update')")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return CommonUtil.view("system/user/userUpdate");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/role")
    @PreAuthorize("hasRole('role:view')")
    public String systemRole() {
        return CommonUtil.view("system/role/role");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/menu")
    @PreAuthorize("hasRole('menu:view')")
    public String systemMenu() {
        return CommonUtil.view("system/menu/menu");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/dept")
    @PreAuthorize("hasRole('dept:view')")
    public String systemDept() {
        return CommonUtil.view("system/dept/dept");
    }

    @RequestMapping(Constant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return CommonUtil.view("index");
    }

    @GetMapping(Constant.VIEW_PREFIX + "404")
    public String error404() {
        return CommonUtil.view("error/404");
    }

    @GetMapping(Constant.VIEW_PREFIX + "403")
    public String error403() {
        return CommonUtil.view("error/403");
    }

    @GetMapping(Constant.VIEW_PREFIX + "500")
    public String error500() {
        return CommonUtil.view("error/500");
    }

    @GetMapping(Constant.VIEW_PREFIX + "system/tProperties/index")
    public String tProperties() {
        return CommonUtil.view("system/properties/index");
    }
    @GetMapping(Constant.VIEW_PREFIX + "system/tProperties/add")
    public String tPropertiesAdd() {
        return CommonUtil.view("system/properties/add");
    }

    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        model.addAttribute("user", user);
        if (transform) {
            String ssex = user.getSex();
            if (User.SEX_MALE.equals(ssex)) {
                user.setSex("男");
            } else if (User.SEX_FEMALE.equals(ssex)) {
                user.setSex("女");
            } else {
                user.setSex("保密");
            }
        }
        if (user.getLastLoginTime() != null) {
            model.addAttribute("lastLoginTime",
                    DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        }
    }
}
