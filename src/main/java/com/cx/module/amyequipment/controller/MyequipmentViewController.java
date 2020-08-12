package com.cx.module.amyequipment.controller;

import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
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
 * 存放设备相关数据 前端控制器
 * amyequipment 前端控制器
 * amyequipment 前端控制器
 *
 * @author admin
 * @Description Created on 2020-08-06
 */
@Controller("amyequipment")
@Slf4j
@RequestMapping(Constant.VIEW_PREFIX + "/amyequipment")
public class MyequipmentViewController extends BaseController {


    @Autowired
    IMyequipmentService iMyequipmentService;

    /**
     * 存放设备相关数据跳转列表页面
     *
     * @param request
     * @param model   com.cx.module.amyequipment.entity
     * @return
     */
    @GetMapping("myequipment/index")
    public String myequipmentIndex(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("amyequipment/myequipment/index");
    }

    @GetMapping("myequipment/index_county")
    public String index_county(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("amyequipment/myequipment/index_county");
    }

    @GetMapping("myequipment/index_township")
    public String myequipmentTownshipIndex(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("amyequipment/myequipment/index_township");
    }

    @GetMapping("myequipment/index_village")
    public String myequipmentVillageIndex(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("amyequipment/myequipment/index_village");
    }

    /**
     * 存放设备相关数据新增页面
     */
    @Autowired
    IMydeptService mydeptService;
    @GetMapping("myequipment/add")
    @PreAuthorize("hasRole('myequipment:add')")
    public String myequipmentAdd(HttpServletRequest request, ModelMap model) {

        User user = CommonUtil.getCurrentUser();
        Long deptId = user.getDeptId();
        Mydept mydept = mydeptService.selectOne(deptId);
        mydept.getCategory();
        return CommonUtil.view("amyequipment/myequipment/add");
    }


    /**
     * 存放设备相关数据 修改页面
     */
    @GetMapping("myequipment/update/{id}")
    @PreAuthorize("hasRole('myequipment:mod')")
    public String myequipmentUpdate(HttpServletRequest request, ModelMap model, @PathVariable Long id) {
        Myequipment obj = iMyequipmentService.selectOne(id);
        System.out.println(">>>>>>>>>>>>>>>>>>>" + obj);
        model.addAttribute("myequipment", obj);
        return CommonUtil.view("amyequipment/myequipment/update");
    }


//    /**
//     * 排名
//     */
//    @GetMapping("myequipment/rank")
////    @PreAuthorize("hasRole('myequipment:add')")
//    public String myequipmentRank(HttpServletRequest request, ModelMap model) {
//        return CommonUtil.view("rank/rank/index");
//    }
//
//
//    @Autowired
//    IMydeptService mydeptService;
//    @Autowired
//    IMyequipmentService myequipmentService;
//
//    /**
//     * 排名柱状图
//     */
//    @GetMapping("myequipment/clu")
////    @PreAuthorize("hasRole('myequipment:add')")
//    public String myequipmentRankclu(HttpServletRequest request, ModelMap model) {
//
//        List<Mydept> mydepts = mydeptService.list(new Mydept());
////        model.addAttribute("mydepts",mydepts);
//        ArrayList<Object> list = new ArrayList<>();
//        ArrayList<Object> deptList = new ArrayList<>();
//        for (Mydept mydept : mydepts) {
//            LambdaQueryWrapper<Myequipment> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Myequipment::getEqDeptId, mydept.getId());
//
//            List<Myequipment> myequipments = myequipmentService.list(wrapper);
//            BigDecimal decimal = new BigDecimal(0);
//            for (Myequipment myequipment : myequipments) {
//                decimal = decimal.add(myequipment.getEqPmTwo());
//            }
//            list.add(decimal);
//            deptList.add(mydept.getName());
//        }
//        model.addAttribute("dataList", list);
//        model.addAttribute("deptList", deptList);
//        return CommonUtil.view("rank/rank/column_copy");
//    }
}
