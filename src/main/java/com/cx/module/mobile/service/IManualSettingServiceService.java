package com.cx.module.mobile.service;

import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.ManualSettingService;
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
public interface IManualSettingServiceService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ManualSettingService selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    ManualSettingService selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<ManualSettingService> list(ManualSettingService obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<ManualSettingService> page(ManualSettingService obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(ManualSettingService obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(ManualSettingService obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(ManualSettingService obj, Wrapper wrapper);

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