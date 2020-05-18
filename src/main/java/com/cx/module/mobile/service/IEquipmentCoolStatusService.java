package com.cx.module.mobile.service;

import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.EquipmentCoolStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Service接口
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
public interface IEquipmentCoolStatusService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    EquipmentCoolStatus selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    EquipmentCoolStatus selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<EquipmentCoolStatus> list(EquipmentCoolStatus obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<EquipmentCoolStatus> page(EquipmentCoolStatus obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(EquipmentCoolStatus obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(EquipmentCoolStatus obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(EquipmentCoolStatus obj, Wrapper wrapper);

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