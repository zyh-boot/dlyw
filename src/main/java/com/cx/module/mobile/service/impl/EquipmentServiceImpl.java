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
import com.cx.module.mobile.entity.Equipment;
import com.cx.module.mobile.mapper.EquipmentMapper;
import com.cx.module.mobile.service.IEquipmentService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service接口实现类
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements IEquipmentService {
    /**
     * 查询详情
     */
    @Override
    public Equipment selectOne(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询详情
     */
    @Override
    public Equipment findSbByCode(String code) {
        return this.baseMapper.findSbByCode(code);
    }

    /**
     * 查询详情
     *
     * @param wrapper
     * @return
     */
    @Override
    public Equipment selectOne(Wrapper wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 查询列表
     */
    @Override
    public List<Equipment> list(Equipment obj) {

        User user = CommonUtil.getCurrentUser();
        LambdaQueryWrapper<Equipment> queryWrapper = new LambdaQueryWrapper<>();
        if(obj.getType()!=null){
            queryWrapper.eq(Equipment::getType,obj.getType());
        }

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Equipment> page(Equipment obj, QueryRequest query) {

        LambdaQueryWrapper<Equipment> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
            queryWrapper.between(Equipment::getCreateDate, obj.getStartDate(), obj.getEndDate());
        }

        Page<Equipment> page = new Page<>(query.getPageNum(), query.getPageSize());
        SortUtil.handlePageSort(query, page, "sbStatus", Constant.ORDER_DESC, true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(Equipment obj) {
        obj.setState(Constant.STATE_1);
        return this.baseMapper.insert(obj);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Equipment obj) {
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
    public int updateByWrapper(Equipment obj, Wrapper wrapper) {
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
        LambdaUpdateWrapper<Equipment> updateWrapper = new UpdateWrapper<Equipment>().lambda();
        updateWrapper.eq(Equipment::getId, id);
        Equipment obj = new Equipment();
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

        LambdaUpdateWrapper<Equipment> updateWrapper = new UpdateWrapper<Equipment>().lambda();
        updateWrapper.in(Equipment::getId, idLists);
        Equipment obj = new Equipment();
        obj.setState(Constant.STATE_0);
        return this.baseMapper.update(obj, updateWrapper);
    }

    @Override
    public List<Map<String,Object>> queryList(Map<String, Object> map) {
        return this.baseMapper.queryList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map) {
        return this.baseMapper.selectCount(map);
    }

    public  List<Map<String,Object>>  querySbList(Map<String,Object> map){
        return this.baseMapper.querySbList(map);
    };

}

