package com.cx.module.myUser.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.myUser.entity.TUser;
import com.cx.module.myUser.service.ITUserService;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import com.cx.common.controller.BaseController;

/**
* 用户表 前端控制器
* myUser 前端控制器
* myUser 前端控制器
* @author admin
* @Description Created on 2020-08-05
*/
@Controller("myUser")
@Slf4j
@RequestMapping( Constant.VIEW_PREFIX + "/myUser")
    public class TUserViewController   extends BaseController{


    @Autowired
    ITUserService iTUserService;

    /**
    * 用户表跳转列表页面
    * @param request
    * @param model com.cx.module.myUser.entity
    * @return
    */
    @GetMapping("tUser/index")
    public String tUserIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("myUser/tUser/index");
    }

    /**
    * 用户表新增页面
    */
    @GetMapping("tUser/add")
    @PreAuthorize("hasRole('tUser:add')")
    public String tUserAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("myUser/tUser/add");
    }

    /**
    *用户表 修改页面
    */
    @GetMapping("tUser/update/{id}")
    @PreAuthorize("hasRole('tUser:mod')")
    public String tUserUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        TUser obj=iTUserService.selectOne(id);
        model.addAttribute("tUser",obj);
        return  CommonUtil.view("myUser/tUser/update");
    }
}
