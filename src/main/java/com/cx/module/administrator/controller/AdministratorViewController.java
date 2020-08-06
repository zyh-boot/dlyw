package com.cx.module.administrator.controller;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.administrator.entity.Administrator;
import com.cx.module.administrator.service.IAdministratorService;
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
*  前端控制器
* administrator 前端控制器
* administrator 前端控制器
* @author admin
* @Description Created on 2020-08-05
*/
@Controller("administrator")
@Slf4j
@RequestMapping( Constant.VIEW_PREFIX + "/administrator")
    public class AdministratorViewController   extends BaseController{


    @Autowired
    IAdministratorService iAdministratorService;

    /**
    * 跳转列表页面
    * @param request
    * @param model com.cx.module.administrator.entity
    * @return
    */
    @GetMapping("administrator/index")
    public String administratorIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("administrator/administrator/index");
    }

    /**
    * 新增页面
    */
    @GetMapping("administrator/add")
    @PreAuthorize("hasRole('administrator:add')")
    public String administratorAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("administrator/administrator/add");
    }

    /**
    * 修改页面
    */
    @GetMapping("administrator/update/{id}")
    @PreAuthorize("hasRole('administrator:mod')")
    public String administratorUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        Administrator obj=iAdministratorService.selectOne(id);
        model.addAttribute("administrator",obj);
        return  CommonUtil.view("administrator/administrator/update");
    }
}
