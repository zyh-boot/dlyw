package com.cx.module.myequipment_history.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.myequipment_history.entity.MyequipmentHistory;

import java.util.List;
/**
* 存放设备相关数据 Service接口
*
* @author admin
* @Description Created on 2020-08-19
*/
public interface IMyequipmentHistoryService{
/**
* 查询详情
*
* @param id
* @return
*/
MyequipmentHistory selectOne(Long id);

/**
* 查询详情
*
* @param wrapper
* @return
*/
MyequipmentHistory selectOne(Wrapper wrapper);


/**
* 查询列表
*
* @param obj
* @return
*/
List<MyequipmentHistory> list(MyequipmentHistory obj);

    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<MyequipmentHistory> list(Wrapper wrapper);

/**
* 分页查询
*
* @param obj
* @param query
* @return
*/
IPage<MyequipmentHistory> page(MyequipmentHistory obj, QueryRequest query);

/**
* 新增
*
* @param obj
* @return
*/
int add(MyequipmentHistory obj);

/**
* 修改
*
* @param obj
* @return
*/
int update(MyequipmentHistory obj);

/**
* 修改
*
* @param wrapper
* @return
*/
int updateByWrapper(MyequipmentHistory obj,Wrapper wrapper);

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
    /**
     * 批量插入
     *
     * @param ids
     * @return
     */
    int batchAddList(List<Myequipment> myequipments);

}