package com.cx.module.mobile.service.impl;

import com.cx.common.entity.Constant;
import com.cx.common.entity.QueryRequest;
import com.cx.common.utils.CommonUtil;
import com.cx.common.utils.SortUtil;
import com.cx.module.mobile.entity.ManualSettingService;
import com.cx.module.mobile.service.IManualSettingServiceService;
import com.cx.module.mobile.mapper.ManualSettingServiceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service接口实现类
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ManualSettingServiceServiceImpl extends ServiceImpl<ManualSettingServiceMapper, ManualSettingService> implements IManualSettingServiceService {
    /**
     * 查询详情
     */
    @Override
    public ManualSettingService selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public ManualSettingService selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<ManualSettingService> list(ManualSettingService obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<ManualSettingService> queryWrapper = new LambdaQueryWrapper<>();

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<ManualSettingService> page(ManualSettingService obj, QueryRequest query) {

        LambdaQueryWrapper<ManualSettingService> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(ManualSettingService::getCreateDate, obj.getStartDate(), obj.getEndDate());
        }

        Page<ManualSettingService> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(ManualSettingService obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ManualSettingService obj) {
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
    public int updateByWrapper(ManualSettingService obj, Wrapper wrapper) {
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
        LambdaUpdateWrapper<ManualSettingService> updateWrapper = new UpdateWrapper<ManualSettingService>().lambda();
        updateWrapper.eq(ManualSettingService::getId, id);
        ManualSettingService obj = new ManualSettingService();
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

        LambdaUpdateWrapper<ManualSettingService> updateWrapper = new UpdateWrapper<ManualSettingService>().lambda();
        updateWrapper.in(ManualSettingService::getId, idLists);
        ManualSettingService obj = new ManualSettingService();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }
}
