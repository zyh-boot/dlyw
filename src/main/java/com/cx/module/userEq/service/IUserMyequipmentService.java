package com.cx.module.userEq.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.userEq.entity.UserMyequipment;

import java.util.List;

/**
 * Service接口
 *
 * @author admin
 * @Description Created on 2020-08-07
 */
public interface IUserMyequipmentService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    UserMyequipment selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    UserMyequipment selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<UserMyequipment> list(UserMyequipment obj);

    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<UserMyequipment> list(Wrapper obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<UserMyequipment> page(UserMyequipment obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(UserMyequipment obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(UserMyequipment obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(UserMyequipment obj, Wrapper wrapper);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);


    int delete(Wrapper wrapper);

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