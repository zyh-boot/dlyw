package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import java.util.List;
import QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import lombok.extern.slf4j.Slf4j;
/**
* ${table.comment!} Service接口
*
* @author ${author}
* @Description Created on ${date}
*/
public interface ${table.serviceName}{
/**
* 查询详情
*
* @param id
* @return
*/
${entity} selectOne(Long id);

/**
* 查询详情
*
* @param wrapper
* @return
*/
${entity} selectOne(Wrapper wrapper);


/**
* 查询列表
*
* @param obj
* @return
*/
List<${entity}> list(${entity} obj);

/**
* 分页查询
*
* @param obj
* @param query
* @return
*/
IPage<${entity}> page(${entity} obj,QueryRequest query);

/**
* 新增
*
* @param obj
* @return
*/
int add(${entity} obj);

/**
* 修改
*
* @param obj
* @return
*/
int update(${entity} obj);

/**
* 修改
*
* @param wrapper
* @return
*/
int updateByWrapper(${entity} obj,Wrapper wrapper);

/**
* 删除
*
* @param id
* @return
*/
int delete(Long id);

/**
* 逻辑删除
*
* @param id
* @return
*/
int logicDel(Long id);


/**
* 批量删除
*
* @param ids
* @return
*/
int batchDel(String ids);

/**
* 批量删除
*
* @param ids
* @return
*/
int batchDelList(List<Long> ids);

    /**
    * 批量逻辑删除
    *
    * @param ids
    * @return
    */
    int batchDelLogic(String ids);

}