package com.cx.module.mobile.service.impl;

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
import com.cx.module.mobile.entity.EquipmentTobaccoHistroy;
import com.cx.module.mobile.mapper.EquipmentTobaccoHistroyMapper;
import com.cx.module.mobile.service.IEquipmentTobaccoHistroyService;
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
 * 烟炕设备历史数据 Service接口实现类
 *
 * @author admin
 * @Description Created on 2020-05-18
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EquipmentTobaccoHistroyServiceImpl extends ServiceImpl<EquipmentTobaccoHistroyMapper, EquipmentTobaccoHistroy> implements IEquipmentTobaccoHistroyService {
    /**
     * 查询详情
     */
    @Override
    public EquipmentTobaccoHistroy selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public EquipmentTobaccoHistroy selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<EquipmentTobaccoHistroy> list(EquipmentTobaccoHistroy obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<EquipmentTobaccoHistroy> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(obj.getPeriodNo())){
            queryWrapper.eq(EquipmentTobaccoHistroy::getPeriodNo,obj.getPeriodNo());
        }
        if(StringUtils.isNotBlank(obj.getCode())){
            queryWrapper.eq(EquipmentTobaccoHistroy::getCode,obj.getCode());
        }
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<EquipmentTobaccoHistroy> page(EquipmentTobaccoHistroy obj, QueryRequest query) {

        LambdaQueryWrapper<EquipmentTobaccoHistroy> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(EquipmentTobaccoHistroy::getCreateDate, obj.getStartDate(), obj.getEndDate());
        }

        if(StringUtils.isNotBlank(obj.getPeriodNo())){
            queryWrapper.eq(EquipmentTobaccoHistroy::getPeriodNo,obj.getPeriodNo());
        }
        if(StringUtils.isNotBlank(obj.getCode())){
            queryWrapper.eq(EquipmentTobaccoHistroy::getCode,obj.getCode());
        }

        Page<EquipmentTobaccoHistroy> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_DESC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(EquipmentTobaccoHistroy obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(EquipmentTobaccoHistroy obj) {
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
    public int updateByWrapper(EquipmentTobaccoHistroy obj, Wrapper wrapper) {
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
        LambdaUpdateWrapper<EquipmentTobaccoHistroy> updateWrapper = new UpdateWrapper<EquipmentTobaccoHistroy>().lambda();
        updateWrapper.eq(EquipmentTobaccoHistroy::getId, id);
        EquipmentTobaccoHistroy obj = new EquipmentTobaccoHistroy();
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

        LambdaUpdateWrapper<EquipmentTobaccoHistroy> updateWrapper = new UpdateWrapper<EquipmentTobaccoHistroy>().lambda();
        updateWrapper.in(EquipmentTobaccoHistroy::getId, idLists);
        EquipmentTobaccoHistroy obj = new EquipmentTobaccoHistroy();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }
}

