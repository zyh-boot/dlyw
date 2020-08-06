package com.cx.module.amyequipment.controller;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
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
* 存放设备相关数据 前端控制器
* amyequipment 前端控制器
* amyequipment 前端控制器
* @author admin
* @Description Created on 2020-08-06
*/
@Controller("amyequipment")
@Slf4j
@RequestMapping( Constant.VIEW_PREFIX + "/amyequipment")
    public class MyequipmentViewController   extends BaseController{


    @Autowired
    IMyequipmentService iMyequipmentService;

    /**
    * 存放设备相关数据跳转列表页面
    * @param request
    * @param model com.cx.module.amyequipment.entity
    * @return
    */
    @GetMapping("myequipment/index")
    public String myequipmentIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("amyequipment/myequipment/index");
    }

    /**
    * 存放设备相关数据新增页面
    */
    @GetMapping("myequipment/add")
    @PreAuthorize("hasRole('myequipment:add')")
    public String myequipmentAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("amyequipment/myequipment/add");
    }

    /**
    *存放设备相关数据 修改页面
    */
    @GetMapping("myequipment/update/{id}")
    @PreAuthorize("hasRole('myequipment:mod')")
    public String myequipmentUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        Myequipment obj=iMyequipmentService.selectOne(id);
        System.out.println(">>>>>>>>>>>>>>>>>>>"+obj);
        model.addAttribute("myequipment",obj);
        return  CommonUtil.view("amyequipment/myequipment/update");
    }
}
