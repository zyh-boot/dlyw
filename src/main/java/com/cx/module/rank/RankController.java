package com.cx.module.rank;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

@RestController
@Slf4j
@RequestMapping("rank/myequipment")
public class RankController extends BaseController {

    @Autowired
    IMydeptService mydeptService;
    @Autowired
    IMyequipmentService myequipmentService;


    /**
     * @param pollution
     * @return
     */
    @GetMapping("rank")
    public CommonResponse getRank(String pollution) {

        ArrayList<Map> list = getMaps(pollution, "", "");
        System.out.println(list);
        return new CommonResponse().data(list);
    }


    /**
     * 污染项数据返回
     *
     * @return
     */
    @GetMapping("pollution")
    public CommonResponse getPollution() {
        ArrayList<Map.Entry<String, BigDecimal>> entries = getEntries("", "");

        return new CommonResponse().data(entries);
    }

    private ArrayList<Map.Entry<String, BigDecimal>> getEntries(String startDate, String endDate) {
        LambdaQueryWrapper<Myequipment> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime start = LocalDateTime.parse(startDate + " 00:00:00", df);
            LocalDateTime end = LocalDateTime.parse(endDate + " 23:59:59", df);
            wrapper.between(Myequipment::getModDate, start, end);
        }


//        List<Myequipment> myequipments = myequipmentService.list(new Myequipment());
        List<Myequipment> myequipments = myequipmentService.list(wrapper);
        HashMap<String, BigDecimal> hashMap = new HashMap<>();
        for (Myequipment myequipment : myequipments) {

            BigDecimal eqPmTwo = myequipment.getEqPmTwo();
            BigDecimal eqPmTen = myequipment.getEqPmTen();
            BigDecimal eqCo = myequipment.getEqCo();
            BigDecimal eqSo2 = myequipment.getEqSo2();
            BigDecimal sqO3 = myequipment.getSqO3();
            BigDecimal sqNo2 = myequipment.getSqNo2();

            hashMap.put("pm2.5", bigDecimalAdd(hashMap.get("pm2.5"), myequipment.getEqPmTwo()));
            hashMap.put("pm10", bigDecimalAdd(hashMap.get("pm10"), myequipment.getEqPmTen()));
            hashMap.put("Co", bigDecimalAdd(hashMap.get("Co"), myequipment.getEqCo()));
            hashMap.put("So2", bigDecimalAdd(hashMap.get("So2"), myequipment.getEqSo2()));
            hashMap.put("O3", bigDecimalAdd(hashMap.get("O3"), myequipment.getSqO3()));
            hashMap.put("No2", bigDecimalAdd(hashMap.get("No2"), myequipment.getSqNo2()));


        }

        ArrayList<Map.Entry<String, BigDecimal>> entries = new ArrayList<>(hashMap.entrySet());
        entries.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        return entries;
    }


    @GetMapping("pollutionSearch")
    public CommonResponse getPollution(String name, String startDate, String endDate) {

        ArrayList<Map.Entry<String, BigDecimal>> maps = getEntries(startDate, endDate);
        ArrayList<Map.Entry<String, BigDecimal>> list = new ArrayList<>();
        if (StringUtils.isNotBlank(name)) {
            for (Map.Entry<String, BigDecimal> map : maps) {
                if (name.equals(map.getKey())) {
                    list.add(map);
                }
            }
        } else {
            list = maps;
        }

        return new CommonResponse().code(HttpStatus.OK).data(list);
    }

    @GetMapping("search")
    public CommonResponse getData(String name, String startDate, String endDate, String type) {

        ArrayList<Map> maps = getMaps(type, startDate, endDate);
        ArrayList<Map> list = new ArrayList<>();
        if (StringUtils.isNotBlank(name)) {
            for (Map map : maps) {
                if (name.equals(map.get("deptName"))) {
                    list.add(map);
                }
            }
        } else {
            list = maps;
        }
        return new CommonResponse().code(HttpStatus.OK).data(list);
    }


    /**
     * 查询pm2.5 pm10数据
     *
     * @param type
     * @return
     */
    private ArrayList<Map> getMaps(String type, String startDate, String endDate) {
        List<Mydept> mydepts = mydeptService.list(new Mydept());

        ArrayList<Map> list = new ArrayList<>();

        for (Mydept mydept : mydepts) {
            HashMap<Object, Object> rankMap = new HashMap<>();
            LambdaQueryWrapper<Myequipment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Myequipment::getEqDeptId, mydept.getId());
            if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime start = LocalDateTime.parse(startDate + " 00:00:00", df);
                LocalDateTime end = LocalDateTime.parse(endDate + " 23:59:59", df);
                wrapper.between(Myequipment::getModDate, start, end);
            }
            List<Myequipment> myequipments = myequipmentService.list(wrapper);
            BigDecimal decimal = new BigDecimal(0);
            for (Myequipment myequipment : myequipments) {
                if ("pm2.5".equals(type)) {
                    decimal = decimal.add(myequipment.getEqPmTwo() == null ? new BigDecimal(0) : myequipment.getEqPmTwo());
                } else if ("pm10".equals(type)) {
                    decimal = decimal.add(myequipment.getEqPmTen() == null ? new BigDecimal(0) : myequipment.getEqPmTen());
                }

            }
//            rankMap.put("ranking",i++);
            rankMap.put("deptName", mydept.getName());
            rankMap.put("deptHeader", mydept.getHead());
            rankMap.put("deptPhone", mydept.getPhone());
            rankMap.put("pollution", decimal);
            list.add(rankMap);
        }
        list.sort(comparing(map -> Double.parseDouble(map.get("pollution") + "")));
        int i = 1;
        for (Map map : list) {
            map.put("ranking", i++);
        }
        return list;
    }

    private BigDecimal bigDecimalAdd(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        bigDecimal1 = bigDecimal1 == null ? new BigDecimal(0) : bigDecimal1;
        bigDecimal2 = bigDecimal2 == null ? new BigDecimal(0) : bigDecimal2;
        return bigDecimal1.add(bigDecimal2);
    }

}
