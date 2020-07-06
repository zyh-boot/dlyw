package com.cx.module.mobile.mapper;

import com.cx.module.mobile.entity.Equipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
*  Mapper接口
*
* @author admin
* @Description Created on 2020-05-08
*/
    public interface EquipmentMapper extends BaseMapper<Equipment> {

       Equipment  findSbByCode(String code);

       List<Map<String,Object>> queryList(Map<String,Object> map);

       int selectCount(Map<String,Object> map);

    List<Map<String,Object>>  querySbList(Map<String,Object> map);
    }
