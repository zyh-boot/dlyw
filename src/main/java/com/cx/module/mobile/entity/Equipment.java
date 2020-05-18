package com.cx.module.mobile.entity;

import com.cx.common.base.entity.BaseEnity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 实体类
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_equipment")
public class Equipment extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备名称")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "设备编号")
    @TableField("code")
    private String code;
    @ApiModelProperty(value = "种类（1.炒货设备）")
    @TableField("type")
    private Integer type;
    @ApiModelProperty(value = "运行状态(1.在线2.离线3.运行中)")
    @TableField("sb_status")
    private Integer sbStatus;
    @ApiModelProperty(value = "经度")
    @TableField("sb_jd")
    private String sbJd;
    @ApiModelProperty(value = "维度")
    @TableField("sb_wd")
    private String sbWd;
    @ApiModelProperty(value = "位置信息")
    @TableField("sb_address")
    private String sbAddress;
    @ApiModelProperty(value = "设备用途")
    @TableField("sb_user")
    private String sbUser;
    @ApiModelProperty(value = "使用时间")
    @TableField("sb_usedate")
    private LocalDateTime sbUsedate;
    @ApiModelProperty(value = "手机卡")
    @TableField("sb_info")
    private String sbInfo;
    @ApiModelProperty(value = "二维码 ")
    @TableField("sb_ewm")
    private String sbEwm;
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "信号")
    @TableField("signal1")
    private Integer signal1;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
