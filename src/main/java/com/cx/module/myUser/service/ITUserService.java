package com.cx.module.myUser.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.QueryRequest;
import com.cx.module.myUser.entity.TUser;

import java.util.List;
/**
* 用户表 Service接口
*
* @author admin
* @Description Created on 2020-08-05
*/
public interface ITUserService{
/**
* 查询详情
*
* @param id
* @return
*/
TUser selectOne(Long id);

/**
* 查询详情
*
* @param wrapper
* @return
*/
TUser selectOne(Wrapper wrapper);


/**
* 查询列表
*
* @param obj
* @return
*/
List<TUser> list(TUser obj);

/**
* 分页查询
*
* @param obj
* @param query
* @return
*/
IPage<TUser> page(TUser obj, QueryRequest query);

/**
* 新增
*
* @param obj
* @return
*/
int add(TUser obj);

/**
* 修改
*
* @param obj
* @return
*/
int update(TUser obj);

/**
* 修改
*
* @param wrapper
* @return
*/
int updateByWrapper(TUser obj,Wrapper wrapper);

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