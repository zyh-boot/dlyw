package com.cx.module.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.utils.CommonUtil;
import com.cx.common.utils.DateUtil;
import com.cx.module.mobile.entity.*;
import com.cx.module.mobile.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP视图以及方法
 *
 * @author Administrator
 */
@Slf4j
@Validated
@RequestMapping("/app")
@RestController
@Api(value = "手机端")
public class AppController extends BaseController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    IEquipmentFeedInfoService iEquipmentFeedInfoService;
    @Autowired
    IEquipmentHeartInfoService equipmentHeartInfoService;
    @Autowired
    IEquipmentRollerInfoService equipmentRollerInfoService;
    @Autowired
    IEquipmentEnergyInfoService equipmentEnergyInfoService;
    @Autowired
    IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService;
    @Autowired
    IEquipmentTobaccoHistroyService iEquipmentTobaccoHistroyService;

    @RequestMapping
    public Object login(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(CommonUtil.view("app/login"));
        return mav;
    }
    @RequestMapping("/index")
    public Object index(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(CommonUtil.view("app/index"));
        return mav;
    }

    @RequestMapping("/go_realtime_page")
    public Object index(HttpServletRequest request, String period, String code, Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        if(StringUtils.isNotEmpty(period)){
            model.addAttribute("work_id",period);
            model.addAttribute("code",code);
        }
        mav.setViewName(CommonUtil.view("app/ScingData"));
        return mav;
    }

    //登录
    @RequestMapping(value = "/login_sub")
    public CommonResponse login_sub(HttpServletRequest request,Account acc) {
        Account u = accountService.findAccountByAccandPwd(acc);
        if(u == null) {
            return new CommonResponse().code(2);
        }
        request.getSession().setAttribute("app_user",u);
        return new CommonResponse().data(u).code(1);
    }


    /**
     * type==1获取炒货机列表  ==2 获取烟炕设备列表
     * @param request
     * @param start
     * @param end
     * @param kh_id
     * @param type
     * @return
     */
    @RequestMapping(value = "/acc_sb")
    @ResponseBody
    public CommonResponse acc_sb(HttpServletRequest request,int start,int end,String kh_id,Integer type) {
        HashMap<String,Object> res=new HashMap<>();
        res.put("start",start);
        res.put("limit",end);
        res.put("type",type);
        res.put("khId",kh_id);
        int count= equipmentService.selectCount(res);
        List<Map<String,Object>> list= equipmentService.queryList(res);
        res.put("count",count);
        res.put("list",list);
        return new CommonResponse().code(1).data(res);
    }

    //获取进料数据
    @RequestMapping(value = "/free_data")
    @ResponseBody
    public CommonResponse free_data(HttpServletRequest request,String code,int pageSize,int pageNum,String period) {
        EquipmentFeedInfo obj =new EquipmentFeedInfo();
        obj.setPeriodId(Long.valueOf(period));
        obj.setSbCode(code);
        QueryRequest query =new QueryRequest();
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        IPage<EquipmentFeedInfo> page = iEquipmentFeedInfoService.page(obj, query);
        return  getTableData(page);
    }

    //获取加热数据
    @RequestMapping(value = "/heart_data")
    @ResponseBody
    public CommonResponse heart_data(HttpServletRequest request,String code,int pageSize,int pageNum,String period) {
        EquipmentHeartInfo obj =new EquipmentHeartInfo();
        obj.setPeriodId(Long.valueOf(period));
        obj.setSbCode(code);
        QueryRequest query =new QueryRequest();
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        IPage<EquipmentHeartInfo> page =equipmentHeartInfoService.page(obj,query);

        return  getTableData(page);
    }

    //滚筒加热数据
    @RequestMapping(value = "/roller_data")
    @ResponseBody
    public CommonResponse roller_data(HttpServletRequest request,String code,int pageSize,int pageNum,String period) {
        EquipmentRollerInfo obj =new EquipmentRollerInfo();
        obj.setPeriodId(Long.valueOf(period));
        obj.setSbCode(code);
        QueryRequest query =new QueryRequest();
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        IPage<EquipmentRollerInfo> page =equipmentRollerInfoService.page(obj,query);

        return  getTableData(page);
    }

    //电力数据
    @RequestMapping(value = "/dl_data")
    @ResponseBody
    public CommonResponse dl_data(HttpServletRequest request,String code,int pageSize,int pageNum,String period) {
        EquipmentEnergyInfo obj =new EquipmentEnergyInfo();
        obj.setPeriodId(Long.valueOf(period));
        obj.setSbCode(code);
        QueryRequest query =new QueryRequest();
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        IPage<EquipmentEnergyInfo> page =equipmentEnergyInfoService.page(obj,query);;
        return getTableData(page);
    }


    //获取进料数据
    @RequestMapping(value = "/tj_free_data")
    @ResponseBody
    public Object tj_free_data(HttpServletRequest request,String code,String period) {
        Map<String,Object> res=new HashMap<>();
        List<String> time =new ArrayList<>();
        List<Float> power =new ArrayList<>();
        List<Float> pl =new ArrayList<>();
        EquipmentFeedInfo obj =new EquipmentFeedInfo();
        obj.setPeriodId(Long.valueOf(period));
        obj.setSbCode(code);
        List<EquipmentFeedInfo> list = iEquipmentFeedInfoService.list(obj);
        for (EquipmentFeedInfo feed:list)
        {
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HH:mm:ss");
            time.add(dtf.format(feed.getCreateDate()));
            power.add(feed.getOutPower());
            pl.add(feed.getRunFrequence());
        }
        res.put("time",time); res.put("power",power); res.put("pl",pl);
        return  res;
    }

    //获取加热数据
    @RequestMapping(value = "/tj_heart_data")
    @ResponseBody
    public Object tj_heart_data(HttpServletRequest request,String code,String period) {
        Map<String,Object> res=new HashMap<>();
        List<String> time =new ArrayList<>();
        List<Integer> power =new ArrayList<>();
        List<Float> wd =new ArrayList<>();
        EquipmentHeartInfo obj =new EquipmentHeartInfo();
        obj.setPeriodId(Long.valueOf(period));
        obj.setSbCode(code);
        List<EquipmentHeartInfo> list = equipmentHeartInfoService.list(obj);
        for (EquipmentHeartInfo h:list){
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HH:mm:ss");
            time.add(dtf.format(h.getCreateDate()));
            power.add(h.getPower());
            wd.add(h.getHeartTemperature());
        };
        res.put("time",time); res.put("power",power); res.put("wd",wd);
        return  res;
    }

    //滚筒数据
    @RequestMapping(value = "/tj_roller_data")
    @ResponseBody
    public Object tj_roller_data(HttpServletRequest request,String code,String period) {
        Map<String,Object> res=new HashMap<>();
        List<String> time =new ArrayList<>();
        List<Float> power =new ArrayList<>();
        List<Float> pl =new ArrayList<>();
        EquipmentRollerInfo obj =new EquipmentRollerInfo();
        obj.setPeriodId(Long.valueOf(period));
        obj.setSbCode(code);
        List<EquipmentRollerInfo> list = equipmentRollerInfoService.list(obj);
        for (EquipmentRollerInfo r:list){
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HH:mm:ss");
            time.add(dtf.format(r.getCreateDate()));
            power.add(r.getOutPower());
            pl.add(r.getRunFrequence());
        };
        res.put("time",time); res.put("power",power); res.put("pl",pl);
        return  res;
    }


    /**
     * 进入烟炕页面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/tobaccoIndex")
    public Object tobaccoIndex(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(CommonUtil.view("app/tobaccoIndex"));
        return mav;
    }

    /**
     * 烟炕设备详细页面
     * @param request
     * @param period
     * @param code
     * @param model
     * @return
     */
    @RequestMapping("/tobaccoDetail")
    public Object tobaccoDetail(HttpServletRequest request, String period, String code, Model model){
        ModelAndView mav = new ModelAndView();
        if(StringUtils.isNotEmpty(period)){
            model.addAttribute("work_id",period);
            model.addAttribute("code",code);
        }
        mav.setViewName(CommonUtil.view("app/tobaccoDetail"));
        return mav;
    }

    /**
     * 获取温湿度列表
     * @param request
     * @param code
     * @param pageSize
     * @param pageNum
     * @param period
     * @return
     */
    @RequestMapping(value = "/getHumitureList")
    @ResponseBody
    public CommonResponse getHumitureList(HttpServletRequest request,String code,int pageSize,int pageNum,String period) {
        EquipmentTobaccoHistroy obj =new EquipmentTobaccoHistroy();
        obj.setPeriodNo(period);
        obj.setCode(code);
        QueryRequest query =new QueryRequest();
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        IPage<EquipmentTobaccoHistroy> page = iEquipmentTobaccoHistroyService.page(obj, query);
        return  getTableData(page);
    }

}
