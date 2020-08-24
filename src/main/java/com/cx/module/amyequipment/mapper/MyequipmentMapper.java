package com.cx.module.amyequipment.mapper;

import com.cx.module.amyequipment.entity.Myequipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* 存放设备相关数据 Mapper接口
*
* @author admin
* @Description Created on 2020-08-06
*/
    public interface MyequipmentMapper extends BaseMapper<Myequipment> {
        int updateBathList(List<Myequipment> list);
    }
