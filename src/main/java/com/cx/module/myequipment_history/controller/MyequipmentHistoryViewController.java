package com.cx.module.myequipment_history.controller;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.myequipment_history.entity.MyequipmentHistory;
import com.cx.module.myequipment_history.service.IMyequipmentHistoryService;
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
* myequipment_history 前端控制器
* myequipment_history 前端控制器
* @author admin
* @Description Created on 2020-08-19
*/
@Controller("myequipment_history")
@Slf4j
@RequestMapping( Constant.VIEW_PREFIX + "/myequipment_history")
    public class MyequipmentHistoryViewController   extends BaseController{


    @Autowired
    IMyequipmentHistoryService iMyequipmentHistoryService;

    /**
    * 存放设备相关数据跳转列表页面
    * @param request
    * @param model com.cx.module.myequipment_history.entity
    * @return
    */
    @GetMapping("myequipmentHistory/index")
    public String myequipmentHistoryIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("myequipment_history/myequipmentHistory/index");
    }

    /**
    * 存放设备相关数据新增页面
    */
    @GetMapping("myequipmentHistory/add")
    @PreAuthorize("hasRole('myequipmentHistory:add')")
    public String myequipmentHistoryAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("myequipment_history/myequipmentHistory/add");
    }

    /**
    *存放设备相关数据 修改页面
    */
    @GetMapping("myequipmentHistory/update/{id}")
    @PreAuthorize("hasRole('myequipmentHistory:mod')")
    public String myequipmentHistoryUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        MyequipmentHistory obj=iMyequipmentHistoryService.selectOne(id);
        model.addAttribute("myequipmentHistory",obj);
        return  CommonUtil.view("myequipment_history/myequipmentHistory/update");
    }
}
