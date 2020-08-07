package com.cx.module.userEq.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.userEq.entity.UserMyequipment;
import com.cx.module.userEq.service.IUserMyequipmentService;

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
*  前端控制器
* userEq 前端控制器
* userEq 前端控制器
* @author admin
* @Description Created on 2020-08-07
*/
@Controller("userEq")
@Slf4j
@RequestMapping( Constant.VIEW_PREFIX + "/userEq")
    public class UserMyequipmentViewController   extends BaseController{


    @Autowired
    IUserMyequipmentService iUserMyequipmentService;

    /**
    * 跳转列表页面
    * @param request
    * @param model com.cx.module.userEq.entity
    * @return
    */
    @GetMapping("userMyequipment/index")
    public String userMyequipmentIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("userEq/userMyequipment/index");
    }

    /**
    * 新增页面
    */
    @GetMapping("userMyequipment/add")
    @PreAuthorize("hasRole('userMyequipment:add')")
    public String userMyequipmentAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("userEq/userMyequipment/add");
    }

    /**
    * 修改页面
    */
    @GetMapping("userMyequipment/update/{id}")
    @PreAuthorize("hasRole('userMyequipment:mod')")
    public String userMyequipmentUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        UserMyequipment obj=iUserMyequipmentService.selectOne(id);
        model.addAttribute("userMyequipment",obj);
        return  CommonUtil.view("userEq/userMyequipment/update");
    }
}
