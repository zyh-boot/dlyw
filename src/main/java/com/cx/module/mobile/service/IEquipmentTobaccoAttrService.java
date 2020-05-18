package com.cx.module.mobile.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.EquipmentTobaccoAttr;

import java.util.List;

/**
 * 烟炕设备属性 Service接口
 *
 * @author admin
 * @Description Created on 2020-05-18
 */
public interface IEquipmentTobaccoAttrService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    EquipmentTobaccoAttr selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    EquipmentTobaccoAttr selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<EquipmentTobaccoAttr> list(EquipmentTobaccoAttr obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<EquipmentTobaccoAttr> page(EquipmentTobaccoAttr obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(EquipmentTobaccoAttr obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(EquipmentTobaccoAttr obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(EquipmentTobaccoAttr obj, Wrapper wrapper);

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

    int addOrUpdate(EquipmentTobaccoAttr obj);
}
