package ${package.Controller};
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* ${table.comment!} 前端控制器
* ${package.ModuleName} 前端控制器
* ${package.ModuleName!} 前端控制器
* @author ${author}
* @Description Created on ${date}
*/
@Controller("${package.ModuleName}")
@Slf4j
@RequestMapping( Constant.VIEW_PREFIX + "/${package.ModuleName}")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    public class ${table.controllerName}   extends ${superControllerClass}{


    @Autowired
    ${table.serviceName} i${entity}Service;

    /**
    * ${table.comment!}跳转列表页面
    * @param request
    * @param model ${package.Entity}
    * @return
    */
    @GetMapping("${entity?uncap_first}/index")
    public String ${table.entityPath}Index(HttpServletRequest request,ModelMap model){
        return CommonUtil.view("${package.ModuleName}/${entity?uncap_first}/index");
    }

    /**
    * ${table.comment!}新增页面
    */
    @GetMapping("${entity?uncap_first}/add")
    @PreAuthorize("hasRole('${entity?uncap_first}:add')")
    public String ${table.entityPath}Add(HttpServletRequest request,ModelMap model){
        return  CommonUtil.view("${package.ModuleName}/${entity?uncap_first}/add");
    }

    /**
    *${table.comment!} 修改页面
    */
    @GetMapping("${entity?uncap_first}/update/{id}")
    @PreAuthorize("hasRole('${entity?uncap_first}:mod')")
    public String ${table.entityPath}Update(HttpServletRequest request,ModelMap model, @PathVariable Long id){
        ${entity} obj=i${entity}Service.selectOne(id);
        model.addAttribute("${entity?uncap_first}",obj);
        return  CommonUtil.view("${package.ModuleName}/${entity?uncap_first}/update");
    }
}
</#if>
