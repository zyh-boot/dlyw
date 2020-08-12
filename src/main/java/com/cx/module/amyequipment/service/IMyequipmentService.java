package com.cx.module.amyequipment.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.amyequipment.entity.Myequipment;

import java.util.List;

/**
 * 存放设备相关数据 Service接口
 *
 * @author admin
 * @Description Created on 2020-08-06
 */
public interface IMyequipmentService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    Myequipment selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    Myequipment selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<Myequipment> list(Myequipment obj);
/**
     * 查询列表
     *
     * @param wrapper
     * @return
     */
    List<Myequipment> list(Wrapper wrapper);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<Myequipment> page(Myequipment obj, QueryRequest query);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<Myequipment> myPage(Myequipment obj, QueryRequest query,List category);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(Myequipment obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(Myequipment obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(Myequipment obj, Wrapper wrapper);

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