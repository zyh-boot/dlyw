package com.cx.module.myUser.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.common.entity.Constant;
import com.cx.common.entity.QueryRequest;
import com.cx.common.utils.CommonUtil;
import com.cx.common.utils.SortUtil;
import com.cx.module.myUser.entity.TUser;
import com.cx.module.myUser.mapper.TUserMapper;
import com.cx.module.myUser.service.ITUserService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表 Service接口实现类
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {
    /**
     * 查询详情
     */
    @Override
    public TUser selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public TUser selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<TUser> list(TUser obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<TUser> queryWrapper = new LambdaQueryWrapper<>();

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<TUser> page(TUser obj, QueryRequest query) {

        LambdaQueryWrapper<TUser> queryWrapper = new LambdaQueryWrapper<>();
//
//        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
//            queryWrapper.between(TUser::getCreateDate, obj.getStartDate(), obj.getEndDate());
//        }
        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(TUser::getOpen, obj.getStartDate(), obj.getEndDate());
        }
        if (StringUtils.isNotBlank(obj.getUsername())) {
            queryWrapper.eq(TUser::getUsername, obj.getUsername());
        }
        if (StringUtils.isNotBlank(obj.getDeptName())) {
            queryWrapper.eq(TUser::getDeptName, obj.getDeptName());
        }
        if (StringUtils.isNotBlank(obj.getWechart())) {
            queryWrapper.eq(TUser::getUsername, obj.getWechart());
        }
        if (StringUtils.isNotBlank(obj.getAccountName())) {
            queryWrapper.eq(TUser::getAccountName, obj.getAccountName());
        }
        if (StringUtils.isNotBlank(obj.getMobile())) {
            queryWrapper.eq(TUser::getMobile, obj.getMobile());
        }

        Page<TUser> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "userId", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(TUser obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(TUser obj) {
        return this.baseMapper.updateById(obj);
    }


    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByWrapper(TUser obj, Wrapper wrapper) {
        return this.baseMapper.update(obj, wrapper);
    }

    /**
     * 删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return this.baseMapper.deleteById(id);
    }


    /**
     * 逻辑删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int logicDel(Long id) {
        System.out.println(">>>>>>>>>>>>" + id);
        LambdaUpdateWrapper<TUser> updateWrapper = new UpdateWrapper<TUser>().lambda();
        updateWrapper.eq(TUser::getUserId, id);
        TUser obj = new TUser();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }

    /**
     * 批量删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDel(String ids) {
        List<Long> idLists = Arrays.stream(ids.split(StringPool.COMMA)).map(s ->
                Long.valueOf(s.trim())).collect(Collectors.toList());
        return batchDelList(idLists);
    }

    /**
     * 批量删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelList(List<Long> ids) {
        return this.baseMapper.deleteBatchIds(ids);
    }

    /**
     * 批量逻辑删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelLogic(String ids) {
        List<Long> idLists = Arrays.stream(ids.split(StringPool.COMMA)).map(s ->
                Long.valueOf(s.trim())).collect(Collectors.toList());

        LambdaUpdateWrapper<TUser> updateWrapper = new UpdateWrapper<TUser>().lambda();
        updateWrapper.in(TUser::getUserId, idLists);
        TUser obj = new TUser();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }
}

