package com.cx.module.amyequipment.service.impl;

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
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.mapper.MyequipmentMapper;
import com.cx.module.amyequipment.service.IMyequipmentService;
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
 * 存放设备相关数据 Service接口实现类
 *
 * @author admin
 * @Description Created on 2020-08-06
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MyequipmentServiceImpl extends ServiceImpl<MyequipmentMapper, Myequipment> implements IMyequipmentService {
    /**
     * 查询详情
     */
    @Override
    public Myequipment selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public Myequipment selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<Myequipment> list(Myequipment obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<Myequipment> queryWrapper = new LambdaQueryWrapper<>();

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<Myequipment> list(Wrapper obj) {

        User user = CommonUtil.getCurrentUser();

        return this.baseMapper.selectList(obj);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Myequipment> page(Myequipment obj, QueryRequest query) {
        LambdaQueryWrapper<Myequipment> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(Myequipment::getEqAddTime, obj.getStartDate(), obj.getEndDate());
        }
        if (StringUtils.isNotBlank(obj.getEqAddress())) {
            queryWrapper.eq(Myequipment::getEqAddress, obj.getEqAddress());
        }
        Page<Myequipment> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 其他分页查询
     */

    @Override
    public IPage<Myequipment> myPage(Myequipment obj, QueryRequest query, List category) {
        LambdaQueryWrapper<Myequipment> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(Myequipment::getEqAddTime, obj.getStartDate(), obj.getEndDate());
        }

        if (StringUtils.isNotBlank(obj.getEqDeptCategory().toString())) {
            queryWrapper.eq(Myequipment::getEqDeptCategory, obj.getEqDeptCategory());
        }
        if (StringUtils.isNotBlank(obj.getEqAddress())) {
//            queryWrapper.eq(Myequipment::getEqAddress, obj.getEqAddress());
            queryWrapper.like(Myequipment::getEqAddress, obj.getEqAddress());
        }

        Page<Myequipment> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }


    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(Myequipment obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Myequipment obj) {
        return this.baseMapper.updateById(obj);
    }

    /**
     * 批量更新数据
     * @param obj
     * @return
     */
    @Override
    public int updateBathList(List<Myequipment> obj) {

        boolean b = this.updateBatchById(obj);
        return b ? 1 : 0;
//        return this.baseMapper.updateBathList(obj);
    }


    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByWrapper(Myequipment obj, Wrapper wrapper) {
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
        LambdaUpdateWrapper<Myequipment> updateWrapper = new UpdateWrapper<Myequipment>().lambda();
        updateWrapper.eq(Myequipment::getId, id);
        Myequipment obj = new Myequipment();
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

        LambdaUpdateWrapper<Myequipment> updateWrapper = new UpdateWrapper<Myequipment>().lambda();
        updateWrapper.in(Myequipment::getId, idLists);
        Myequipment obj = new Myequipment();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }
}

