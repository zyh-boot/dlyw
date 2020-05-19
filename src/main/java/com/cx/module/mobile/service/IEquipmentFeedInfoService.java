package com.cx.module.mobile.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.EquipmentFeedInfo;

import java.util.List;

/**
 * Service接口
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
public interface IEquipmentFeedInfoService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    EquipmentFeedInfo selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    EquipmentFeedInfo selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<EquipmentFeedInfo> list(EquipmentFeedInfo obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<EquipmentFeedInfo> page(EquipmentFeedInfo obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(EquipmentFeedInfo obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(EquipmentFeedInfo obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(EquipmentFeedInfo obj, Wrapper wrapper);

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
