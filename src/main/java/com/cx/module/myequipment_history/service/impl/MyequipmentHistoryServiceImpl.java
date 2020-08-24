package com.cx.module.myequipment_history.service.impl;

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
import com.cx.module.myequipment_history.entity.MyequipmentHistory;
import com.cx.module.myequipment_history.mapper.MyequipmentHistoryMapper;
import com.cx.module.myequipment_history.service.IMyequipmentHistoryService;
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
 * @Description Created on 2020-08-19
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MyequipmentHistoryServiceImpl extends ServiceImpl<MyequipmentHistoryMapper, MyequipmentHistory> implements IMyequipmentHistoryService {
    /**
     * 查询详情
     */
    @Override
    public MyequipmentHistory selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public MyequipmentHistory selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<MyequipmentHistory> list(MyequipmentHistory obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<MyequipmentHistory> queryWrapper = new LambdaQueryWrapper<>();

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<MyequipmentHistory> list(Wrapper wrapper) {
        return this.baseMapper.selectList(wrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<MyequipmentHistory> page(MyequipmentHistory obj, QueryRequest query) {

        LambdaQueryWrapper<MyequipmentHistory> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(MyequipmentHistory::getCreateDate, obj.getStartDate(), obj.getEndDate());
        }

        Page<MyequipmentHistory> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(MyequipmentHistory obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(MyequipmentHistory obj) {
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
    public int updateByWrapper(MyequipmentHistory obj, Wrapper wrapper) {
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
        LambdaUpdateWrapper<MyequipmentHistory> updateWrapper = new UpdateWrapper<MyequipmentHistory>().lambda();
        updateWrapper.eq(MyequipmentHistory::getId, id);
        MyequipmentHistory obj = new MyequipmentHistory();
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

        LambdaUpdateWrapper<MyequipmentHistory> updateWrapper = new UpdateWrapper<MyequipmentHistory>().lambda();
        updateWrapper.in(MyequipmentHistory::getId, idLists);
        MyequipmentHistory obj = new MyequipmentHistory();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }

    @Override
    public int batchAddList(List<Myequipment> myequipments) {
        return this.baseMapper.bathAddList(myequipments);
    }
}

