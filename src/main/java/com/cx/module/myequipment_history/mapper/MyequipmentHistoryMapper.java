package com.cx.module.myequipment_history.mapper;

import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.myequipment_history.entity.MyequipmentHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* 存放设备相关数据 Mapper接口
*
* @author admin
* @Description Created on 2020-08-19
*/
    public interface MyequipmentHistoryMapper extends BaseMapper<MyequipmentHistory> {
        int bathAddList(List<Myequipment> myequipments);
    }
