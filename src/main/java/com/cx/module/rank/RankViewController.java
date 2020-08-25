package com.cx.module.rank;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import com.cx.module.myequipment_history.service.IMyequipmentHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller("rank")
@Slf4j
@RequestMapping(Constant.VIEW_PREFIX + "/rank")
public class RankViewController extends BaseController {
    @Autowired
    IMydeptService mydeptService;
    @Autowired
    IMyequipmentService myequipmentService;
    @Autowired
    IMyequipmentHistoryService myequipmentHistoryService;

    /**
     * 排名
     */
    @GetMapping("myequipment/rank")
    public String myequipmentRank(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("rank/rank/index");
    }

    /**
     * 排名
     */
    @GetMapping("myequipment/pollution")
    public String myequipmentPollution(HttpServletRequest request, ModelMap model) {
        return CommonUtil.view("rank/rank/pollution");
    }

    /**
     * 机构下数据详情
     */
    @GetMapping("myequipment/detiles/{id}")
    public String myequipmentDetiles(HttpServletRequest request, ModelMap model, @PathVariable String id) {
        model.addAttribute("id",id);
        return CommonUtil.view("rank/rank/column");
    }



    /**
     * 排名柱状图
     */
    @GetMapping("myequipment/clu")
//    @PreAuthorize("hasRole('myequipment:add')")
    public String myequipmentRankclu(HttpServletRequest request, ModelMap model) {

        List<Mydept> mydepts = mydeptService.list(new Mydept());
        ArrayList<Object> list = new ArrayList<>();
        ArrayList<Object> deptList = new ArrayList<>();
        for (Mydept mydept : mydepts) {
            LambdaQueryWrapper<Myequipment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Myequipment::getEqDeptId, mydept.getId());

            List<Myequipment> myequipments = myequipmentService.list(wrapper);
            BigDecimal decimal = new BigDecimal(0);
            for (Myequipment myequipment : myequipments) {
                decimal = decimal.add(myequipment.getEqPmTwo());
            }
            list.add(decimal);
            deptList.add(mydept.getName());
        }
        model.addAttribute("dataList", list);
        model.addAttribute("deptList", deptList);
        return CommonUtil.view("rank/rank/column_copy");
    }

    /**
     * 污染项排名柱状图
     */
    @GetMapping("myequipment/pollutionEchart")
    public String myequipmentPollutionEchart(HttpServletRequest request, ModelMap model) {

        List<Myequipment> myequipments = myequipmentService.list(new Myequipment());


        ArrayList<BigDecimal> list = getBigDecimals(myequipments);
        model.addAttribute("dataList",list);
        return CommonUtil.view("rank/rank/column_pollution");
//        return new CommonResponse().data(entries);
    }

    private ArrayList<BigDecimal> getBigDecimals(List<Myequipment> myequipments) {
        ArrayList<BigDecimal> list = new ArrayList<>();
        for (Myequipment myequipment : myequipments) {
            if(list.size() == 6){
                list.set(0,list.get(0).add(bigDecimalIsNull(myequipment.getEqPmTwo())));
                list.set(1,list.get(1).add(bigDecimalIsNull(myequipment.getEqPmTen())));
                list.set(2,list.get(2).add(bigDecimalIsNull(myequipment.getEqCo())));
                list.set(3,list.get(3).add(bigDecimalIsNull(myequipment.getEqSo2())));
                list.set(4,list.get(4).add(bigDecimalIsNull(myequipment.getSqO3())));
                list.set(5,list.get(5).add(bigDecimalIsNull(myequipment.getSqNo2())));
                list.set(6,list.get(6).add(bigDecimalIsNull(myequipment.getEqNoise())));
                list.set(7,list.get(7).add(bigDecimalIsNull(new BigDecimal(myequipment.getWindSpeed()))));

            }else {
                list.add(0,bigDecimalIsNull(myequipment.getEqPmTwo()));
                list.add(1,bigDecimalIsNull(myequipment.getEqPmTen()));
                list.add(2,bigDecimalIsNull(myequipment.getEqCo()));
                list.add(3,bigDecimalIsNull(myequipment.getEqSo2()));
                list.add(4,bigDecimalIsNull(myequipment.getSqO3()));
                list.add(5,bigDecimalIsNull(myequipment.getSqNo2()));
                list.add(6,bigDecimalIsNull(myequipment.getEqNoise()));
                list.add(7,bigDecimalIsNull(new BigDecimal(myequipment.getWindSpeed())));
            }
        }
        return list;
    }

    private BigDecimal bigDecimalIsNull(BigDecimal bigDecimal) {
        return bigDecimal == null ? new BigDecimal(0) : bigDecimal;
    }
}
