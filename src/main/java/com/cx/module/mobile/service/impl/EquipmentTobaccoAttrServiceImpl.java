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
import com.cx.module.mobile.entity.EquipmentTobaccoAttr;
import com.cx.module.mobile.entity.EquipmentTobaccoHistroy;
import com.cx.module.mobile.mapper.EquipmentTobaccoAttrMapper;
import com.cx.module.mobile.service.IEquipmentTobaccoAttrService;
import com.cx.module.mobile.service.IEquipmentTobaccoHistroyService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 烟炕设备属性 Service接口实现类
 *
 * @author admin
 * @Description Created on 2020-05-18
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EquipmentTobaccoAttrServiceImpl extends ServiceImpl<EquipmentTobaccoAttrMapper, EquipmentTobaccoAttr> implements IEquipmentTobaccoAttrService {

    @Autowired
    IEquipmentTobaccoHistroyService iEquipmentTobaccoHistroyService;

    /**
     * 查询详情
     */
    @Override
    public EquipmentTobaccoAttr selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public EquipmentTobaccoAttr selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<EquipmentTobaccoAttr> list(EquipmentTobaccoAttr obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<EquipmentTobaccoAttr> queryWrapper = new LambdaQueryWrapper<>();

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<EquipmentTobaccoAttr> page(EquipmentTobaccoAttr obj, QueryRequest query) {

        LambdaQueryWrapper<EquipmentTobaccoAttr> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(EquipmentTobaccoAttr::getCreateDate, obj.getStartDate(), obj.getEndDate());
        }

        Page<EquipmentTobaccoAttr> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(EquipmentTobaccoAttr obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(EquipmentTobaccoAttr obj) {
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
    public int updateByWrapper(EquipmentTobaccoAttr obj, Wrapper wrapper) {
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
        LambdaUpdateWrapper<EquipmentTobaccoAttr> updateWrapper = new UpdateWrapper<EquipmentTobaccoAttr>().lambda();
        updateWrapper.eq(EquipmentTobaccoAttr::getId, id);
        EquipmentTobaccoAttr obj = new EquipmentTobaccoAttr();
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

        LambdaUpdateWrapper<EquipmentTobaccoAttr> updateWrapper = new UpdateWrapper<EquipmentTobaccoAttr>().lambda();
        updateWrapper.in(EquipmentTobaccoAttr::getId, idLists);
        EquipmentTobaccoAttr obj = new EquipmentTobaccoAttr();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }

    @Override
    public int addOrUpdate(EquipmentTobaccoAttr obj) {
        LambdaQueryWrapper<EquipmentTobaccoAttr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EquipmentTobaccoAttr::getCode, obj.getCode());
        EquipmentTobaccoAttr attr = this.baseMapper.selectOne(queryWrapper);
        if (attr != null & attr.getId() != null) {
            obj.setId(attr.getId());
            this.baseMapper.updateById(obj);
        } else {
            this.baseMapper.insert(obj);
        }
        EquipmentTobaccoHistroy history = new EquipmentTobaccoHistroy();
        history.setCode(obj.getCode());
        history.setFanCurrent(obj.getFanCurrent());
        history.setCompressorCurrentOne(obj.getCompressorCurrentOne());
        history.setCompressorCurrentTwo(obj.getCompressorCurrentTwo());
        history.setSystemVoltage(obj.getSystemVoltage());
        history.setUpTemperature(obj.getUpTemperature());
        history.setUpHumidity(obj.getUpHumidity());
        history.setDownTemperature(obj.getDownTemperature());
        history.setDownHumidity(obj.getDownHumidity());
        history.setPipelineTemperatureOne(obj.getPipelineTemperatureOne());
        history.setPipelineTemperatureTwo(obj.getPipelineTemperatureTwo());
        history.setWorkPeriodId(obj.getWorkPeriodId());
        iEquipmentTobaccoHistroyService.add(history);
        return Constant.STATE_1;
    }
}

