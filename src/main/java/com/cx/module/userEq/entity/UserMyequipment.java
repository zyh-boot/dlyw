package com.cx.module.userEq.entity;

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
 * @Description Created on 2020-08-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_user_myequipment")
public class UserMyequipment extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "管理员ID")
    @TableField("user_id")
    private Integer userId;
    @ApiModelProperty(value = "设备ID")
    @TableField("myequipment_id")
    private Integer myequipmentId;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
