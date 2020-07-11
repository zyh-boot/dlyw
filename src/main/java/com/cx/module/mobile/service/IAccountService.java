package com.cx.module.mobile.service;

import com.cx.common.entity.QueryRequest;
import com.cx.module.mobile.entity.Account;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cx.module.mobile.entity.AccountEquipment;

/**
 * Service接口
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
public interface IAccountService {
    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    Account selectOne(Long id);

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    Account selectOne(Wrapper wrapper);


    /**
     * 查询列表
     *
     * @param obj
     * @return
     */
    List<Account> list(Account obj);

    /**
     * 分页查询
     *
     * @param obj
     * @param query
     * @return
     */
    IPage<Account> page(Account obj, QueryRequest query);


    /**
     * 自定义分页查询列表
     * @param map
     * @return
     */

    List<Account>  pageList(Map<String,Object> map);
    /**
     * 新增
     *
     * @param obj
     * @return
     */
    int add(Account obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    int update(Account obj);

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    int updateByWrapper(Account obj, Wrapper wrapper);

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

    Account  findAccountByAccandPwd(Account map) ;

}