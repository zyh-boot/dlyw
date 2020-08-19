package com.cx.module.myUser.Scheduled;

import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Configuration
public class MyUserTask {

    @Autowired
    IMyequipmentService myequipmentService;


    private String PM_TWO = "";
    private String PM_TEN = "";
    private String CO = "";
    private String SO2 = "";
    private String NO2 = "";
    private String O3 = "";
    private Boolean FLAG = true;
    ArrayList<Object> list = new ArrayList<>();

    @Scheduled(cron = " 59 */59 * * * ?")
    public void uadetaMyequipment() {
        List<Myequipment> myequipments = myequipmentService.list(new Myequipment());

        for (Myequipment myequipment : myequipments) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            if (FLAG) {
                hashMap.put("PM_TWO",isNull(myequipment.getEqPmTwo()));
                hashMap.put("PM_TEN",isNull(myequipment.getEqPmTen()));
                hashMap.put("CO",isNull(myequipment.getEqCo()));
                hashMap.put("SO2",isNull(myequipment.getEqSo2()));
                hashMap.put("NO2",isNull(myequipment.getSqNo2()));
                hashMap.put("O3",isNull(myequipment.getSqO3()));

                list.add(hashMap);

                PM_TWO = isNull(myequipment.getEqPmTwo());
                PM_TEN = isNull(myequipment.getEqPmTen());
                CO = isNull(myequipment.getEqCo());
                SO2 = isNull(myequipment.getEqSo2());
                NO2 = isNull(myequipment.getSqNo2());
                O3 = isNull(myequipment.getSqO3());
            } else {
                if (
                        PM_TWO.equals(isNull(myequipment.getEqPmTwo())) &&
                        PM_TEN.equals(isNull(myequipment.getEqPmTen())) &&
                        CO.equals(isNull(myequipment.getEqCo())) &&
                        SO2.equals(isNull(myequipment.getEqSo2())) &&
                        NO2.equals(isNull(myequipment.getSqNo2())) &&
                        O3.equals(isNull(myequipment.getSqO3()))
                ) {
                    log.info(">>>>>>>>>>>>>>>>>>设备超时<<<<<<<<<<<<<<<<<<<<<<<<");
                }
            }

        }
        FLAG = false;
    }


    private String isNull(BigDecimal str) {
        return str == null ? "0" : str.toString();
    }
}
