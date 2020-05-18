package com.cx.module.mobile.service;

import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.Equipment;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * Service接口
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
public interface IEquipmentService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    Equipment selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    Equipment selectOne(Wrapper wrapper);

    /**
     * 查询详情
     *
     * @param code
     * @return
     */
    Equipment  findSbByCode(String code);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<Equipment> list(Equipment obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<Equipment> page(Equipment obj, QueryRequest query);

    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(Equipment obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(Equipment obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(Equipment obj, Wrapper wrapper);

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


    List<Map<String,Object>> queryList(Map<String,Object> map);

    int selectCount(Map<String,Object> map);

}