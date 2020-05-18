package com.cx.module.mobile.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.EquipmentTobaccoHistroy;

import java.util.List;

/**
 * 烟炕设备历史数据 Service接口
 *
 * @author admin
 * @Description Created on 2020-05-18
 */
public interface IEquipmentTobaccoHistroyService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    EquipmentTobaccoHistroy selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    EquipmentTobaccoHistroy selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<EquipmentTobaccoHistroy> list(EquipmentTobaccoHistroy obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<EquipmentTobaccoHistroy> page(EquipmentTobaccoHistroy obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(EquipmentTobaccoHistroy obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(EquipmentTobaccoHistroy obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(EquipmentTobaccoHistroy obj, Wrapper wrapper);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int logicDel(Long id);


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int batchDel(String ids);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int batchDelList(List<Long> ids);

    /**
     * 批量逻辑删除
     *
     * @param ids
     * @return
     */
    int batchDelLogic(String ids);

}
