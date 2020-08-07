package com.cx.module.userEq.service.impl;

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
import com.cx.module.userEq.entity.UserMyequipment;
import com.cx.module.userEq.mapper.UserMyequipmentMapper;
import com.cx.module.userEq.service.IUserMyequipmentService;
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
 * Service接口实现类
 *
 * @author admin
 * @Description Created on 2020-08-07
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserMyequipmentServiceImpl extends ServiceImpl<UserMyequipmentMapper, UserMyequipment> implements IUserMyequipmentService {
    /**
     * 查询详情
     */
    @Override
    public UserMyequipment selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public UserMyequipment selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<UserMyequipment> list(UserMyequipment obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<UserMyequipment> queryWrapper = new LambdaQueryWrapper<>();

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询列表
     * 自定义条件
     */
    @Override
    public List<UserMyequipment> list(Wrapper wrapper) {

        User user = CommonUtil.getCurrentUser();
        return this.baseMapper.selectList(wrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<UserMyequipment> page(UserMyequipment obj, QueryRequest query) {

        LambdaQueryWrapper<UserMyequipment> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(UserMyequipment::getCreateDate, obj.getStartDate(), obj.getEndDate());
        }

        Page<UserMyequipment> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(UserMyequipment obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(UserMyequipment obj) {
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
    public int updateByWrapper(UserMyequipment obj, Wrapper wrapper) {
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
     * 删除
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Wrapper wrapper) {
        return this.baseMapper.delete(wrapper);
//        return this.baseMapper.deleteById(id);
    }


    /**
     * 逻辑删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int logicDel(Long id) {
        LambdaUpdateWrapper<UserMyequipment> updateWrapper = new UpdateWrapper<UserMyequipment>().lambda();
        updateWrapper.eq(UserMyequipment::getId, id);
        UserMyequipment obj = new UserMyequipment();
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

        LambdaUpdateWrapper<UserMyequipment> updateWrapper = new UpdateWrapper<UserMyequipment>().lambda();
        updateWrapper.in(UserMyequipment::getId, idLists);
        UserMyequipment obj = new UserMyequipment();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }
}

