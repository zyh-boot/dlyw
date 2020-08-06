package com.cx.module.mydept.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cx.common.base.entity.BaseEnity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_mydept")
public class Mydept extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "机构ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "机构名称")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "机构负责人")
    @TableField("head")
    private String head;
    @ApiModelProperty(value = "机构地址")
    @TableField("address")
    private String address;
    @ApiModelProperty(value = "机构类别 1:市 2:县 3:乡 4:村")
    @TableField("category")
    private Integer category;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
