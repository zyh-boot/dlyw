package com.cx.module.mydept.service.impl;

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
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.mapper.MydeptMapper;
import com.cx.module.mydept.service.IMydeptService;
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
 * @Description Created on 2020-08-05
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MydeptServiceImpl extends ServiceImpl<MydeptMapper, Mydept> implements IMydeptService {
    /**
     * 查询详情
     */
    @Override
    public Mydept selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public Mydept selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<Mydept> list(Mydept obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<Mydept> queryWrapper = new LambdaQueryWrapper<>();

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Mydept> page(Mydept obj, QueryRequest query) {

        LambdaQueryWrapper<Mydept> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(Mydept::getCreateDate, obj.getStartDate(), obj.getEndDate());
        }
        if (StringUtils.isNotBlank(obj.getName())) {
            queryWrapper.eq(Mydept::getName, obj.getName());
        }
        if (StringUtils.isNotBlank(obj.getHead())) {
            queryWrapper.eq(Mydept::getHead, obj.getHead());
        }
        if (StringUtils.isNotBlank(obj.getAddress())) {
            queryWrapper.eq(Mydept::getAddress, obj.getAddress());
        }
        if (obj.getCategory() != null) {
            queryWrapper.eq(Mydept::getCategory, obj.getCategory());
        }

        Page<Mydept> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(Mydept obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Mydept obj) {
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
    public int updateByWrapper(Mydept obj, Wrapper wrapper) {
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
        LambdaUpdateWrapper<Mydept> updateWrapper = new UpdateWrapper<Mydept>().lambda();
        updateWrapper.eq(Mydept::getId, id);
        Mydept obj = new Mydept();
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

        LambdaUpdateWrapper<Mydept> updateWrapper = new UpdateWrapper<Mydept>().lambda();
        updateWrapper.in(Mydept::getId, idLists);
        Mydept obj = new Mydept();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }
}

