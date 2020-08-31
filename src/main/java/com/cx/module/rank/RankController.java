package com.cx.module.rank;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import com.cx.module.myequipment_history.entity.MyequipmentHistory;
import com.cx.module.myequipment_history.service.IMyequipmentHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

/**
 * 排名
 */
@RestController
@Slf4j
@RequestMapping("rank/myequipment")
public class RankController extends BaseController {

    @Autowired
    IMydeptService mydeptService;
    @Autowired
    IMyequipmentService myequipmentService;
    @Autowired
    IMyequipmentHistoryService myequipmentHistoryService;


    /**
     * 页面初始化数据
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


    /**
     * 各个污染项数据 从小到大排序返回
     * @param startDate
     * @param endDate
     * @return
     */
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

//            BigDecimal eqPmTwo = myequipment.getEqPmTwo();
//            BigDecimal eqPmTen = myequipment.getEqPmTen();
//            BigDecimal eqCo = myequipment.getEqCo();
//            BigDecimal eqSo2 = myequipment.getEqSo2();
//            BigDecimal sqO3 = myequipment.getSqO3();
//            BigDecimal sqNo2 = myequipment.getSqNo2();

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


    /**
     * 污染项排名页面查询操作
     * @param name
     * @param startDate
     * @param endDate
     * @return
     */
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

    /**
     * 机构污染排名页面查询
     * @param name
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
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

    /**
     * 校验数据为空 赋默认值
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    private BigDecimal bigDecimalAdd(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        bigDecimal1 = bigDecimal1 == null ? new BigDecimal(0) : bigDecimal1;
        bigDecimal2 = bigDecimal2 == null ? new BigDecimal(0) : bigDecimal2;
        return bigDecimal1.add(bigDecimal2);
    }

    /**
     * 返回echarts图表所需数据
     * 每个数据用了一个列表保存,可优化
     * @param id
     * @param data
     * @return
     */
    @GetMapping("detile")
    public CommonResponse getDetile(String id, String data) {
        LambdaQueryWrapper<MyequipmentHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MyequipmentHistory::getEqId, Long.parseLong(id))
                .likeRight(MyequipmentHistory::getModDate, data == null ? LocalDate.now() : data)
                .orderByAsc(MyequipmentHistory::getModDate);
        List<MyequipmentHistory> list = myequipmentHistoryService.list(wrapper);

        ArrayList<Object> pmtwo = new ArrayList<>();
        ArrayList<Object> pmten = new ArrayList<>();
        ArrayList<Object> co = new ArrayList<>();
        ArrayList<Object> so2 = new ArrayList<>();
        ArrayList<Object> no2 = new ArrayList<>();
        ArrayList<Object> o3 = new ArrayList<>();
        ArrayList<Object> windSpeed = new ArrayList<>();
        ArrayList<Object> noise = new ArrayList<>();


        if (!list.isEmpty()){
            int hour = list.get(0).getModDate().getHour();
            if (hour != 0) {
                for (int i = 0; i < hour; i++) {
                    setZero(pmtwo, pmten, co, so2, no2, o3, windSpeed, noise);
                }
            }

                for (int j = 0; j < list.size(); j++) {
                    if (j == 0) {
                        pmtwo.add(getBigDecimal(list.get(j).getEqPmTwo()));
                        pmten.add(getBigDecimal(list.get(j).getEqPmTen()));
                        co.add(getBigDecimal(list.get(j).getEqCo()));
                        so2.add(getBigDecimal(list.get(j).getEqSo2()));
                        no2.add(getBigDecimal(list.get(j).getSqNo2()));
                        o3.add(getBigDecimal(list.get(j).getSqO3()));
                        windSpeed.add(getBigDecimal(list.get(j).getWindSpeed()));
                        noise.add(getBigDecimal(list.get(j).getEqNoise()));
                    } else {
                        int start = list.get(j - 1).getModDate().getHour();
                        int end = list.get(j).getModDate().getHour();
                        if (end-start>1){
                            for(int k=0;k<end-start;k++){
                                setZero(pmtwo, pmten, co, so2, no2, o3, windSpeed, noise);
                            }
                            pmtwo.add(getBigDecimal(list.get(j).getEqPmTwo()));
                            pmten.add(getBigDecimal(list.get(j).getEqPmTen()));
                            co.add(getBigDecimal(list.get(j).getEqCo()));
                            so2.add(getBigDecimal(list.get(j).getEqSo2()));
                            no2.add(getBigDecimal(list.get(j).getSqNo2()));
                            o3.add(getBigDecimal(list.get(j).getSqO3()));
                            windSpeed.add(getBigDecimal(list.get(j).getWindSpeed()));
                            noise.add(getBigDecimal(list.get(j).getEqNoise()));
                        }else if(end-start==0){
                            pmtwo.set(pmtwo.size()-1,getBigDecimal(list.get(j).getEqPmTwo()));
                            pmten.set(pmten.size()-1,getBigDecimal(list.get(j).getEqPmTen()));
                            co.set(co.size()-1,getBigDecimal(list.get(j).getEqCo()));
                            so2.set(so2.size()-1,getBigDecimal(list.get(j).getEqSo2()));
                            no2.set(no2.size()-1,getBigDecimal(list.get(j).getSqNo2()));
                            o3.set(o3.size()-1,getBigDecimal(list.get(j).getSqO3()));
                            windSpeed.set(windSpeed.size()-1,getBigDecimal(list.get(j).getWindSpeed()));
                            noise.set(noise.size()-1,getBigDecimal(list.get(j).getEqNoise()));
                        }else{
                            pmtwo.add(getBigDecimal(list.get(j).getEqPmTwo()));
                            pmten.add(getBigDecimal(list.get(j).getEqPmTen()));
                            co.add(getBigDecimal(list.get(j).getEqCo()));
                            so2.add(getBigDecimal(list.get(j).getEqSo2()));
                            no2.add(getBigDecimal(list.get(j).getSqNo2()));
                            o3.add(getBigDecimal(list.get(j).getSqO3()));
                            windSpeed.add(getBigDecimal(list.get(j).getWindSpeed()));
                            noise.add(getBigDecimal(list.get(j).getEqNoise()));
                        }
                    }
                }

        }else{
            for(int l=0;l<24;l++){
                setZero(pmtwo, pmten, co, so2, no2, o3, windSpeed, noise);
            }
        }



        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("pmtwo", pmtwo);
        hashMap.put("pmten", pmten);
        hashMap.put("co", co);
        hashMap.put("so2", so2);
        hashMap.put("no2", no2);
        hashMap.put("o3", o3);
        hashMap.put("windSpeed", windSpeed);
        hashMap.put("noise", noise);
        return new CommonResponse().code(HttpStatus.OK).data(hashMap);
    }

    private void setZero(ArrayList<Object> pmtwo, ArrayList<Object> pmten, ArrayList<Object> co, ArrayList<Object> so2, ArrayList<Object> no2, ArrayList<Object> o3, ArrayList<Object> windSpeed, ArrayList<Object> noise) {
        pmtwo.add(0);
        pmten.add(0);
        co.add(0);
        so2.add(0);
        no2.add(0);
        o3.add(0);
        windSpeed.add(0);
        noise.add(0);
    }

    private BigDecimal getBigDecimal(BigDecimal bigDecimal){
        return bigDecimal == null ? new BigDecimal(0) : bigDecimal;
    }
}
