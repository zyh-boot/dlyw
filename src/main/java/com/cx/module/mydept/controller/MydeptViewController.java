package com.cx.module.mydept.controller;

import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.myUser.entity.TUser;
import com.cx.module.myUser.service.ITUserService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 前端控制器
 * mydept 前端控制器
 * mydept 前端控制器
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@Controller("mydept")
@Slf4j
@RequestMapping(Constant.VIEW_PREFIX + "/mydept")
public class MydeptViewController extends BaseController {


    @Autowired
    IMydeptService iMydeptService;
    @Autowired
    ITUserService userService;

    /**
     * 跳转列表页面
     *
     * @param request
     * @param model   com.cx.module.mydept.entity
     * @return
     */
    @GetMapping("mydept/index")
    public String mydeptIndex(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("mydept/mydept/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("mydept/add")
    @PreAuthorize("hasRole('mydept:add')")
    public String mydeptAdd(HttpServletRequest request, ModelMap model) {
        User user = CommonUtil.getCurrentUser();

        TUser tUser = userService.selectOne(user.getUserId());
        Long deptId = tUser.getDeptId();
        Mydept mydept = iMydeptService.selectOne(deptId);
        if (mydept == null) {
            return CommonUtil.view("error/403");
        }
        return CommonUtil.view("mydept/mydept/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("mydept/update/{id}")
    @PreAuthorize("hasRole('mydept:mod')")
    public String mydeptUpdate(HttpServletRequest request, ModelMap model, @PathVariable Long id) {
        Mydept obj = iMydeptService.selectOne(id);
        model.addAttribute("mydept", obj);
        Boolean s = null;

        return CommonUtil.view("mydept/mydept/update");
    }
}
