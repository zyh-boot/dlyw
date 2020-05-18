package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
<#if entityLombokModel>
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
</#if>

/**
* ${table.comment!} 实体类
*
* @author ${author}
* @Description Created on ${date}
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("${table.comment!}")
<#if superEntityClass??>
@TableName("${table.name}")
public class ${entity} extends ${superEntityClass}  implements Serializable {
</#if>
    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if "create_date" != field.name
    && "mod_date" != field.name && "state" != field.name
    && "creator" != field.name && "modifier" != field.name>
        <#if field.keyFlag>
            <#assign keyPropertyName="${field.propertyName}"/>
        </#if>
        <#if field.comment!?length gt 0>
            <#if swagger2>
                @ApiModelProperty(value = "${field.comment}")
            <#else>
                /**
                * ${field.comment}
                */
            </#if>
        </#if>
        <#if field.keyFlag>
            <#if field.keyIdentityFlag>
                @TableId(value = "${field.name}", type = IdType.AUTO)
            <#elseif field.convert>
                @TableId("${field.name}")
            </#if>
        <#-- 普通字段 -->
        <#elseif field.fill??>
            /**
            */
        <#-- -----   存在字段填充设置   ----->
            <#if field.convert>
                @TableField(value = "${field.name}", fill = FieldFill.${field.fill})
            <#else>
                @TableField(fill = FieldFill.${field.fill})
            </#if>
        <#else>
            @TableField("${field.name}")
        </#if>
    <#-- 乐观锁注解 -->
        <#if (versionFieldName!"") == field.name>
            @Version
        </#if>
    <#-- 逻辑删除注解 -->
        <#if (logicDeleteFieldName!"") == field.name>
            @TableLogic
        </#if>
        private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->

@TableField(exist = false)
private String startDate;

@TableField(exist = false)
private String endDate;
}
