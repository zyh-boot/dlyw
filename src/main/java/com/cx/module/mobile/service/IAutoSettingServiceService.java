package com.cx.module.mobile.service;

import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.AutoSettingService;
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
public interface IAutoSettingServiceService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    AutoSettingService selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    AutoSettingService selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<AutoSettingService> list(AutoSettingService obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<AutoSettingService> page(AutoSettingService obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(AutoSettingService obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(AutoSettingService obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(AutoSettingService obj, Wrapper wrapper);

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