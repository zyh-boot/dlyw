package com.cx.module.mydept.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.mydept.entity.Mydept;

import java.util.List;
/**
*  Service接口
*
* @author admin
* @Description Created on 2020-08-05
*/
public interface IMydeptService{
/**
* 查询详情
*
* @param id
* @return
*/
Mydept selectOne(Long id);

/**
* 查询详情
*
* @param wrapper
* @return
*/
Mydept selectOne(Wrapper wrapper);


/**
* 查询列表
*
* @param obj
* @return
*/
List<Mydept> list(Mydept obj);

/**
* 分页查询
*
* @param obj
* @param query
* @return
*/
IPage<Mydept> page(Mydept obj, QueryRequest query);

/**
* 新增
*
* @param obj
* @return
*/
int add(Mydept obj);

/**
* 修改
*
* @param obj
* @return
*/
int update(Mydept obj);

/**
* 修改
*
* @param wrapper
* @return
*/
int updateByWrapper(Mydept obj,Wrapper wrapper);

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