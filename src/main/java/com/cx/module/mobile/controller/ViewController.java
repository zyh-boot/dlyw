package com.cx.module.mobile.controller;

import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.mobile.entity.*;
import com.cx.module.mobile.service.*;
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
 * 前端控制器
 * mobile 前端控制器
 * mobile 前端控制器
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
@Controller("mobile")
@Slf4j
@RequestMapping(Constant.VIEW_PREFIX + "/mobile")
public class ViewController extends BaseController {
    @Autowired
    IAccountService iAccountService;
    @Autowired
    IAccountEquipmentService iAccountEquipmentService;
    /**
     * 跳转列表页面
     *
     * @param request
     * @param model   com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("account/index")
    public String accountIndex(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("mobile/account/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("account/add")
    @PreAuthorize("hasRole('account:add')")
    public String accountAdd(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("mobile/account/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("account/update/{id}")
    @PreAuthorize("hasRole('account:mod')")
    public String accountUpdate(HttpServletRequest request, ModelMap model, @PathVariable Long id) {
        Account obj = iAccountService.selectOne(id);
        model.addAttribute("account", obj);
        return CommonUtil.view("mobile/account/update");
    }

    /**
     * 修改页面
     */
    @GetMapping("account/map")
//    @PreAuthorize("hasRole('account:mod')")
    public String accountMap(HttpServletRequest request, ModelMap model) {


        return CommonUtil.view("mobile/account/chooseAddress");
    }




    /**
     * 绑定客户设备页面（这里不做，一台设备只能一个客户绑定的逻辑，等客户需要再做处理）
     */
    @GetMapping("account/kh_code/{id}")
    @PreAuthorize("hasRole('account:sb')")
    public String accountSb(HttpServletRequest request, ModelMap model, @PathVariable Long id) {
        Account obj = iAccountService.selectOne(id);
        model.addAttribute("account", obj);
        List<String> sbList=new ArrayList<>();
        //获取客户的设备
        AccountEquipment ae=new AccountEquipment();
        ae.setKhId(obj.getId());
        List<AccountEquipment>  accountEquipmentList=iAccountEquipmentService.list(ae);
        if(accountEquipmentList!=null&&accountEquipmentList.size()>0){
            int num=accountEquipmentList.size();
            for (int i = 0; i <num; i++) {
                AccountEquipment equ=accountEquipmentList.get(i);
                sbList.add(equ.getSbCode()+"");
            }
        }
        //获取设备列表
        Equipment sb=new Equipment();
        List<Equipment>  list=   iEquipmentService.list(sb);
        List<CodeBean> codeList=new ArrayList<>();
        if(list!=null&&list.size()>0){
            int num=list.size();
            for (int i = 0; i <num; i++) {
                Equipment equ=list.get(i);
                CodeBean cb=new CodeBean();
                cb.setValue(equ.getCode()+"");
                cb.setTitle(equ.getCode());
                codeList.add(cb);
            }
        }
        model.addAttribute("sbList", sbList);
        model.addAttribute("codeList", codeList);
        return CommonUtil.view("mobile/account/kh_code");
    }



    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("accountEquipment/index")
    public String accountEquipmentIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/accountEquipment/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("accountEquipment/add")
    @PreAuthorize("hasRole('accountEquipment:add')")
    public String accountEquipmentAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/accountEquipment/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("accountEquipment/update/{id}")
    @PreAuthorize("hasRole('accountEquipment:mod')")
    public String accountEquipmentUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        AccountEquipment obj=iAccountEquipmentService.selectOne(id);
        model.addAttribute("accountEquipment",obj);
        return  CommonUtil.view("mobile/accountEquipment/update");
    }
    @Autowired
    IEquipmentService iEquipmentService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipment/index")
    public String equipmentIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipment/index");
    }

    /**
     * 跳转实时数据页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipment/data")
    public String equipment(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipment/data");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipment/add")
    @PreAuthorize("hasRole('equipment:add')")
    public String equipmentAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipment/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipment/update/{id}")
    @PreAuthorize("hasRole('equipment:mod')")
    public String equipmentUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        Equipment obj=iEquipmentService.selectOne(id);
        model.addAttribute("equipment",obj);
        return  CommonUtil.view("mobile/equipment/update");
    }

    @Autowired
    IAutoSettingServiceService iAutoSettingServiceService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("autoSettingService/index")
    public String autoSettingServiceIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/autoSettingService/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("autoSettingService/add")
    @PreAuthorize("hasRole('autoSettingService:add')")
    public String autoSettingServiceAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/autoSettingService/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("autoSettingService/update/{id}")
    @PreAuthorize("hasRole('autoSettingService:mod')")
    public String autoSettingServiceUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        AutoSettingService obj=iAutoSettingServiceService.selectOne(id);
        model.addAttribute("autoSettingService",obj);
        return  CommonUtil.view("mobile/autoSettingService/update");
    }

    @Autowired
    IEquipmentCoolStatusService iEquipmentCoolStatusService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentCoolStatus/index")
    public String equipmentCoolStatusIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentCoolStatus/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentCoolStatus/add")
    @PreAuthorize("hasRole('equipmentCoolStatus:add')")
    public String equipmentCoolStatusAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentCoolStatus/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentCoolStatus/update/{id}")
    @PreAuthorize("hasRole('equipmentCoolStatus:mod')")
    public String equipmentCoolStatusUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentCoolStatus obj=iEquipmentCoolStatusService.selectOne(id);
        model.addAttribute("equipmentCoolStatus",obj);
        return  CommonUtil.view("mobile/equipmentCoolStatus/update");
    }

    @Autowired
    IEquipmentEnergyInfoService iEquipmentEnergyInfoService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentEnergyInfo/index")
    public String equipmentEnergyInfoIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentEnergyInfo/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentEnergyInfo/add")
    @PreAuthorize("hasRole('equipmentEnergyInfo:add')")
    public String equipmentEnergyInfoAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentEnergyInfo/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentEnergyInfo/update/{id}")
    @PreAuthorize("hasRole('equipmentEnergyInfo:mod')")
    public String equipmentEnergyInfoUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentEnergyInfo obj=iEquipmentEnergyInfoService.selectOne(id);
        model.addAttribute("equipmentEnergyInfo",obj);
        return  CommonUtil.view("mobile/equipmentEnergyInfo/update");
    }

    @Autowired
    IEquipmentFeedInfoService iEquipmentFeedInfoService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentFeedInfo/index")
    public String equipmentFeedInfoIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentFeedInfo/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentFeedInfo/add")
    @PreAuthorize("hasRole('equipmentFeedInfo:add')")
    public String equipmentFeedInfoAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentFeedInfo/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentFeedInfo/update/{id}")
    @PreAuthorize("hasRole('equipmentFeedInfo:mod')")
    public String equipmentFeedInfoUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentFeedInfo obj=iEquipmentFeedInfoService.selectOne(id);
        model.addAttribute("equipmentFeedInfo",obj);
        return  CommonUtil.view("mobile/equipmentFeedInfo/update");
    }
    @Autowired
    IEquipmentHeartInfoService iEquipmentHeartInfoService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentHeartInfo/index")
    public String equipmentHeartInfoIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentHeartInfo/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentHeartInfo/add")
    @PreAuthorize("hasRole('equipmentHeartInfo:add')")
    public String equipmentHeartInfoAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentHeartInfo/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentHeartInfo/update/{id}")
    @PreAuthorize("hasRole('equipmentHeartInfo:mod')")
    public String equipmentHeartInfoUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentHeartInfo obj=iEquipmentHeartInfoService.selectOne(id);
        model.addAttribute("equipmentHeartInfo",obj);
        return  CommonUtil.view("mobile/equipmentHeartInfo/update");
    }

    @Autowired
    IEquipmentRollerInfoService iEquipmentRollerInfoService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentRollerInfo/index")
    public String equipmentRollerInfoIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentRollerInfo/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentRollerInfo/add")
    @PreAuthorize("hasRole('equipmentRollerInfo:add')")
    public String equipmentRollerInfoAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentRollerInfo/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentRollerInfo/update/{id}")
    @PreAuthorize("hasRole('equipmentRollerInfo:mod')")
    public String equipmentRollerInfoUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentRollerInfo obj=iEquipmentRollerInfoService.selectOne(id);
        model.addAttribute("equipmentRollerInfo",obj);
        return  CommonUtil.view("mobile/equipmentRollerInfo/update");
    }
    @Autowired
    IEquipmentFeedSetServiceService iEquipmentFeedSetServiceService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentFeedSetService/index")
    public String equipmentFeedSetServiceIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentFeedSetService/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentFeedSetService/add")
    @PreAuthorize("hasRole('equipmentFeedSetService:add')")
    public String equipmentFeedSetServiceAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentFeedSetService/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentFeedSetService/update/{id}")
    @PreAuthorize("hasRole('equipmentFeedSetService:mod')")
    public String equipmentFeedSetServiceUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentFeedSetService obj=iEquipmentFeedSetServiceService.selectOne(id);
        model.addAttribute("equipmentFeedSetService",obj);
        return  CommonUtil.view("mobile/equipmentFeedSetService/update");
    }
    @Autowired
    IEquipmentWorkPeriodService iEquipmentWorkPeriodService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentWorkPeriod/index")
    public String equipmentWorkPeriodIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentWorkPeriod/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentWorkPeriod/add")
    @PreAuthorize("hasRole('equipmentWorkPeriod:add')")
    public String equipmentWorkPeriodAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentWorkPeriod/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentWorkPeriod/update/{id}")
    @PreAuthorize("hasRole('equipmentWorkPeriod:mod')")
    public String equipmentWorkPeriodUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentWorkPeriod obj=iEquipmentWorkPeriodService.selectOne(id);
        model.addAttribute("equipmentWorkPeriod",obj);
        return  CommonUtil.view("mobile/equipmentWorkPeriod/update");
    }
    @Autowired
    IEquipmentHeartSetServiceService iEquipmentHeartSetServiceService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentHeartSetService/index")
    public String equipmentHeartSetServiceIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentHeartSetService/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentHeartSetService/add")
    @PreAuthorize("hasRole('equipmentHeartSetService:add')")
    public String equipmentHeartSetServiceAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentHeartSetService/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentHeartSetService/update/{id}")
    @PreAuthorize("hasRole('equipmentHeartSetService:mod')")
    public String equipmentHeartSetServiceUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentHeartSetService obj=iEquipmentHeartSetServiceService.selectOne(id);
        model.addAttribute("equipmentHeartSetService",obj);
        return  CommonUtil.view("mobile/equipmentHeartSetService/update");
    }
    @Autowired
    IEquipmentRollerSetServiceService iEquipmentRollerSetServiceService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentRollerSetService/index")
    public String equipmentRollerSetServiceIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentRollerSetService/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("equipmentRollerSetService/add")
    @PreAuthorize("hasRole('equipmentRollerSetService:add')")
    public String equipmentRollerSetServiceAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentRollerSetService/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("equipmentRollerSetService/update/{id}")
    @PreAuthorize("hasRole('equipmentRollerSetService:mod')")
    public String equipmentRollerSetServiceUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentRollerSetService obj=iEquipmentRollerSetServiceService.selectOne(id);
        model.addAttribute("equipmentRollerSetService",obj);
        return  CommonUtil.view("mobile/equipmentRollerSetService/update");
    }  @Autowired
    IWorkPeriodYcService iWorkPeriodYcService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("workPeriodYc/index")
    public String workPeriodYcIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/workPeriodYc/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("workPeriodYc/add")
    @PreAuthorize("hasRole('workPeriodYc:add')")
    public String workPeriodYcAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/workPeriodYc/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("workPeriodYc/update/{id}")
    @PreAuthorize("hasRole('workPeriodYc:mod')")
    public String workPeriodYcUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        WorkPeriodYc obj=iWorkPeriodYcService.selectOne(id);
        model.addAttribute("workPeriodYc",obj);
        return  CommonUtil.view("mobile/workPeriodYc/update");
    }
    @Autowired
    IStepSettingServiceService iStepSettingServiceService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("stepSettingService/index")
    public String stepSettingServiceIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/stepSettingService/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("stepSettingService/add")
    @PreAuthorize("hasRole('stepSettingService:add')")
    public String stepSettingServiceAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/stepSettingService/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("stepSettingService/update/{id}")
    @PreAuthorize("hasRole('stepSettingService:mod')")
    public String stepSettingServiceUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        StepSettingService obj=iStepSettingServiceService.selectOne(id);
        model.addAttribute("stepSettingService",obj);
        return  CommonUtil.view("mobile/stepSettingService/update");
    }

    @Autowired
    IManualSettingServiceService iManualSettingServiceService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("manualSettingService/index")
    public String manualSettingServiceIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/manualSettingService/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("manualSettingService/add")
    @PreAuthorize("hasRole('manualSettingService:add')")
    public String manualSettingServiceAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/manualSettingService/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("manualSettingService/update/{id}")
    @PreAuthorize("hasRole('manualSettingService:mod')")
    public String manualSettingServiceUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        ManualSettingService obj=iManualSettingServiceService.selectOne(id);
        model.addAttribute("manualSettingService",obj);
        return  CommonUtil.view("mobile/manualSettingService/update");
    }



    /************************烟炕设备属性***********************/
    @Autowired
    IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService;

    /**
     * 烟炕设备属性跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentTobaccoAttr/index")
    public String equipmentTobaccoAttrIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentTobaccoAttr/index");
    }

    /**
     * 烟炕设备属性新增页面
     */
    @GetMapping("equipmentTobaccoAttr/add")
    @PreAuthorize("hasRole('equipmentTobaccoAttr:add')")
    public String equipmentTobaccoAttrAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentTobaccoAttr/add");
    }

    /**
     *烟炕设备属性 修改页面
     */
    @GetMapping("equipmentTobaccoAttr/update/{id}")
    @PreAuthorize("hasRole('equipmentTobaccoAttr:mod')")
    public String equipmentTobaccoAttrUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentTobaccoAttr obj=iEquipmentTobaccoAttrService.selectOne(id);
        model.addAttribute("equipmentTobaccoAttr",obj);
        return  CommonUtil.view("mobile/equipmentTobaccoAttr/update");
    }

    /************************烟炕设备历史数据************************/

    @Autowired
    IEquipmentTobaccoHistroyService iEquipmentTobaccoHistroyService;

    /**
     * 烟炕设备历史数据跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("equipmentTobaccoHistroy/index")
    public String equipmentTobaccoHistroyIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/equipmentTobaccoHistroy/index");
    }

    /**
     * 烟炕设备历史数据新增页面
     */
    @GetMapping("equipmentTobaccoHistroy/add")
    @PreAuthorize("hasRole('equipmentTobaccoHistroy:add')")
    public String equipmentTobaccoHistroyAdd(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("mobile/equipmentTobaccoHistroy/add");
    }

    /**
     *烟炕设备历史数据 修改页面
     */
    @GetMapping("equipmentTobaccoHistroy/update/{id}")
    @PreAuthorize("hasRole('equipmentTobaccoHistroy:mod')")
    public String equipmentTobaccoHistroyUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        EquipmentTobaccoHistroy obj=iEquipmentTobaccoHistroyService.selectOne(id);
        model.addAttribute("equipmentTobaccoHistroy",obj);
        return  CommonUtil.view("mobile/equipmentTobaccoHistroy/update");
    }
    @Autowired
    ISendCommandService iSendCommandService;

    /**
     * 跳转列表页面
     * @param request
     * @param model com.cx.module.mobile.entity
     * @return
     */
    @GetMapping("sendCommand/index")
    public String sendCommandIndex(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("mobile/sendCommand/index");
    }

    /**
     * 新增页面
     */
    @GetMapping("sendCommand/add")
    @PreAuthorize("hasRole('sendCommand:add')")
    public String sendCommandAdd(HttpServletRequest request,ModelMap model){
          Equipment entrity=new Equipment();
          entrity.setType(2);
          model.addAttribute("codes",iEquipmentService.list(entrity));
          return  CommonUtil.view("mobile/sendCommand/add");
    }

    /**
     * 修改页面
     */
    @GetMapping("sendCommand/update/{id}")
    @PreAuthorize("hasRole('sendCommand:mod')")
    public String sendCommandUpdate(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        SendCommand obj=iSendCommandService.selectOne(id);
        model.addAttribute("sendCommand",obj);
        return  CommonUtil.view("mobile/sendCommand/update");
    }
}
