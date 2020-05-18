package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import User;
import Constant;
import QueryRequest;
import CommonUtil;
import SortUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import User;
/**
* ${table.comment!} Service接口实现类
*
* @author ${author}
* @Description Created on ${date}
*/
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName},${entity}> implements ${table.serviceName} {
/**
* 查询详情
*/
@Override
public ${entity} selectOne(Long id){
return this.baseMapper.selectById(id);
}


/**
* 查询详情
*
* @param wrapper
* @return
*/
@Override
public ${entity} selectOne(Wrapper wrapper){
  return this.baseMapper.selectOne(wrapper);
}

/**
* 查询列表
*/
@Override
public List<${entity}> list(${entity} obj){

User user = CommonUtil.getCurrentUser();
LambdaQueryWrapper<${entity}> queryWrapper=new LambdaQueryWrapper<>();

return this.baseMapper.selectList(queryWrapper);
}
/**
* 分页查询
*/
@Override
public IPage<${entity}> page(${entity} obj,QueryRequest query){

LambdaQueryWrapper<${entity}> queryWrapper=new LambdaQueryWrapper<>();

if (StringUtils.isNotBlank(obj.getStartDate()) && StringUtils.isNotBlank(obj.getEndDate())) {
queryWrapper.between(${entity} ::getCreateDate, obj.getStartDate(), obj.getEndDate());
}

Page<${entity}> page = new Page<>(query.getPageNum(), query.getPageSize());
<#list table.fields as field>
    <#if field.keyIdentityFlag>
        SortUtil.handlePageSort(query, page, "${field.propertyName}", Constant.ORDER_ASC, true);
        <#break>
    <#elseif  "create_date" != field.name>
        SortUtil.handlePageSort(query, page, "${field.propertyName}", Constant.ORDER_ASC, true);
        <#break>
    <#else>
        SortUtil.handlePageSort(query, page, "id", Constant.ORDER_ASC, true);
        <#break>
    </#if>
</#list>
return this.baseMapper.selectPage(page, queryWrapper);
}

/**
* 新增
*/
@Override
@Transactional(rollbackFor = Exception.class)
public int add(${entity} obj){
obj.setState(Constant.STATE_1);
return this.baseMapper.insert(obj);
}
/**
* 修改
*/
@Override
@Transactional(rollbackFor = Exception.class)
public int update(${entity} obj){
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
public int updateByWrapper(${entity} obj,Wrapper wrapper){
return this.baseMapper.update(obj,wrapper);
}

/**
* 删除
*/
@Override
@Transactional(rollbackFor = Exception.class)
public int delete(Long id){
return this.baseMapper.deleteById(id);
}




/**
* 逻辑删除
*/
@Override
@Transactional(rollbackFor = Exception.class)
public int logicDel(Long id){
LambdaUpdateWrapper<${entity}> updateWrapper = new UpdateWrapper<${entity}>().lambda();
updateWrapper.eq(${entity}::getId, id);
${entity} obj = new ${entity}();
obj.setState(Constant.STATE_0);
return this.baseMapper.update(obj, updateWrapper);
}

/**
* 批量删除
*/
@Override
@Transactional(rollbackFor = Exception.class)
public  int batchDel(String ids){
List<Long> idLists =Arrays.stream(ids.split(StringPool.COMMA)).map(s ->
    Long.valueOf(s.trim())).collect(Collectors.toList());
    return batchDelList(idLists);
}

    /**
    * 批量删除
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelList(List<Long> ids){
        return this.baseMapper.deleteBatchIds(ids);
    }
        /**
        * 批量逻辑删除
        */
        @Override
        @Transactional(rollbackFor = Exception.class)
        public int batchDelLogic(String ids){
        List<Long> idLists =Arrays.stream(ids.split(StringPool.COMMA)).map(s ->
            Long.valueOf(s.trim())).collect(Collectors.toList());

            LambdaUpdateWrapper<${entity}> updateWrapper = new UpdateWrapper<${entity}>().lambda();
            updateWrapper.in(${entity}::getId, idLists);
            ${entity} obj = new ${entity}();
            obj.setState(Constant.STATE_0);
            return this.baseMapper.update(obj, updateWrapper);
            }
    }

