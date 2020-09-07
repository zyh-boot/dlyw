package com.cx.module.myUser.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import com.cx.module.mobile.entity.CodeBean;
import com.cx.module.myUser.entity.TUser;
import com.cx.module.myUser.service.ITUserService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import com.cx.module.userEq.entity.UserMyequipment;
import com.cx.module.userEq.service.IUserMyequipmentService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 用户表 前端控制器
 * myUser 前端控制器
 * myUser 前端控制器
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@Controller("myUser")
@Slf4j
@RequestMapping(Constant.VIEW_PREFIX + "/myUser")
public class TUserViewController extends BaseController {


    @Autowired
    ITUserService iTUserService;
    @Autowired
    IUserMyequipmentService userMyequipmentService;
    @Autowired
    IMyequipmentService myequipmentService;
    @Autowired
    IMydeptService mydeptService;

    /**
     * 用户表跳转列表页面
     *
     * @param request
     * @param model   com.cx.module.myUser.entity
     * @return
     */
    @GetMapping("tUser/index")
    public String tUserIndex(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("myUser/tUser/index");
    }

    /**
     * 用户表新增页面
     */
    @GetMapping("tUser/add")
    @PreAuthorize("hasRole('tUser:add')")
    public String tUserAdd(HttpServletRequest request, ModelMap model) {
        User user = CommonUtil.getCurrentUser();
        TUser tUser = iTUserService.selectOne(user.getUserId());
        Long deptId = tUser.getDeptId();
        Mydept mydept = mydeptService.selectOne(deptId);
        if( mydept == null){
            return CommonUtil.view("error/403");
        }

        return CommonUtil.view("myUser/tUser/add");
    }

    /**
     * 用户表 修改页面
     */
    @GetMapping("tUser/update/{id}")
    @PreAuthorize("hasRole('tUser:mod')")
    public String tUserUpdate(HttpServletRequest request, ModelMap model, @PathVariable Long id) {
        TUser obj = iTUserService.selectOne(id);
        model.addAttribute("tUser", obj);
        return CommonUtil.view("myUser/tUser/update");
    }

    /**
     * 用户表 设备绑定页面
     */
    @GetMapping("tUser/bind/{id}")
    @PreAuthorize("hasRole('tUser:bind')")
    public String tUserBind(HttpServletRequest request, ModelMap model, @PathVariable Integer id) {
        TUser obj = iTUserService.selectOne(id.longValue());
        model.addAttribute("tUser", obj);

        //查询当前用户下的设备
        LambdaQueryWrapper<UserMyequipment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserMyequipment::getUserId, id);
        List<UserMyequipment> list = userMyequipmentService.list(wrapper);

        ArrayList<Object> dataList = new ArrayList<>();
        for (UserMyequipment userMyequipment1 : list) {
            if (userMyequipment1 != null) {
                dataList.add(userMyequipment1.getMyequipmentId());
            }
        }
/****************************查找已绑定设备Id***************************************/
        ArrayList<String> allList = new ArrayList<>();
        List<Myequipment> myequipmentList = myequipmentService.list(new Myequipment());
        for (Myequipment myequipment : myequipmentList) {
            allList.add(myequipment.getId().toString());
        }

        ArrayList<String> nowList = new ArrayList<>();
        List<UserMyequipment> userMyequipments = userMyequipmentService.list(new UserMyequipment());
        for (UserMyequipment userMyequipment : userMyequipments) {
            nowList.add(userMyequipment.getMyequipmentId().toString());
        }

        allList.retainAll(nowList);
        System.out.println(">>>>>>>>>>>>>>>>>>>>" + allList);
/*******************************************************************/

        List<Myequipment> myequipments = myequipmentService.list(new Myequipment());
        ArrayList<CodeBean> listCode = new ArrayList<>();
        for (Myequipment myequipment : myequipments) {
            CodeBean codeBean = new CodeBean();
            codeBean.setTitle(myequipment.getEqName());
            codeBean.setValue(myequipment.getId().toString());

            /**********************判断设备是否在自己名下********************/
            if (allList.indexOf(myequipment.getId().toString()) >= 0) {
                LambdaQueryWrapper<UserMyequipment> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(UserMyequipment::getUserId, id)
                        .eq(UserMyequipment::getMyequipmentId, myequipment.getId());
                UserMyequipment userMyequipment = userMyequipmentService.selectOne(wrapper1);
                //不在自己名下不可绑定
                if (userMyequipment == null) {
                    codeBean.setDisabled(true);
                }
            }
            /*************************************************************/
            listCode.add(codeBean);
        }

        model.addAttribute("codeList", listCode);
        model.addAttribute("dataList", dataList);
        return CommonUtil.view("myUser/tUser/bind");
    }
}
