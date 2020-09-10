package com.cx.module.myequipment_history.task;

import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import com.cx.module.myequipment_history.service.IMyequipmentHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务: 每个小时更新a_myequipment_history(设备数据历史表)
 * <p>
 * <p>
 */
@Slf4j
@Configuration
public class MyScheduled {

    @Autowired
    IMyequipmentService myequipmentService;
    @Autowired
    IMyequipmentHistoryService myequipmentHistoryService;

    /**
     * 一个小时更新一次数据
     */
    @Scheduled(cron = " 59 59 * * * ?")
//    @Scheduled(cron = " 59 */5 * * * ?")
    public void uadetaEntoryUsed() {
        List<Myequipment> list = myequipmentService.list(new Myequipment());
        myequipmentHistoryService.batchAddList(list);
    }

    //测试模拟设备数据传递
//    @Scheduled(cron = " 59 */4 * * * ?")
    public void addda() {
        log.info("************数据发送************");
        List<Myequipment> list = myequipmentService.list(new Myequipment());
        ArrayList<Myequipment> list1 = new ArrayList<>();
        for(Myequipment myequipment : list){
            myequipment.setEqPmTwo(getaLong());
            myequipment.setEqPmTen(getaLong());
            myequipment.setEqCo(getaLong());
            myequipment.setSqNo2(getaLong());
            myequipment.setEqSo2(getaLong());
            myequipment.setSqO3(getaLong());
            myequipment.setWindSpeed(getaLong().toString());
            myequipment.setWindDirection(getaLong().toString());
            myequipment.setEqNoise(getaLong());
            list1.add(myequipment);
        }
        myequipmentService.updateBathList(list1);
    }

    private BigDecimal getaLong() {
        long l = Math.round(Math.random() * 100);
        BigDecimal bigDecimal = new BigDecimal(Math.round(Math.random()) * l);
        return bigDecimal;
    }


}
