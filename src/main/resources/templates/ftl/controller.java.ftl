package ${package.Controller};
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* ${table.comment!}  控制器
*
* @author ${author}
* @Description Created on ${date}
*/
@RestController
@Slf4j
@RequestMapping("${package.ModuleName}/${entity?uncap_first}")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass}{
    <#else>
        public class ${table.controllerName} {
    </#if>
    @Autowired
    ${table.serviceName} i${entity}Service;

    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
        try {
            return getCommonResponse(i${entity}Service.selectOne(id));
        } catch (Exception e) {
            String message = "查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 分页查询
    */
    @GetMapping("pageList")
    public CommonResponse pageList(${entity} obj,QueryRequest query)  throws CommonException{
        try {
            return  getTableData(i${entity}Service.page(obj,query));
        } catch (Exception e) {
            String message = "分页查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 查询列表
    */
    @GetMapping("list")
    public CommonResponse pageList(${entity} obj)  throws CommonException{
        try {
            return getCommonResponse(i${entity}Service.list(obj));
        } catch (Exception e) {
            String message = "列表查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 新增
    */
    @PostMapping("")
    @PreAuthorize("hasRole('${entity?uncap_first}:add')")
    public CommonResponse add(${entity} obj) throws CommonException{
        try {
            return getCommonResponse(i${entity}Service.add(obj));
        } catch (Exception e) {
            String message = "新增失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }


    /**
    * 修改
    */
    @PutMapping("")
    @PreAuthorize("hasRole('${entity?uncap_first}:mod')")
    public CommonResponse update(${entity} obj) throws CommonException{
        try {
            return getCommonResponse(i${entity}Service.update(obj));
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 删除
    * @param ids
    * @return
    */
    @DeleteMapping("")
    @PreAuthorize("hasRole('${entity?uncap_first}:del')")
    public CommonResponse delete(String ids) throws CommonException{
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    i${entity}Service.batchDel(ids);
                } else {
                    i${entity}Service.delete(Long.valueOf(ids));
                }
            }
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

}
</#if>
