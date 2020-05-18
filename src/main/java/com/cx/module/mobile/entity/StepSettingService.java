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
@TableName("a_step_setting_service")
public class StepSettingService extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备code")
    @TableField("sb_code")
    private String sbCode;
    @ApiModelProperty(value = "炒货周期")
    @TableField("period_id")
    private Long periodId;
    @ApiModelProperty(value = "过温保护点")
    @TableField("over_temp_protect")
    private Integer overTempProtect;
    @ApiModelProperty(value = "阶段个数")
    @TableField("step_num")
    private Integer stepNum;
    @ApiModelProperty(value = "阶段温度阀值1")
    @TableField("temp_thr1")
    private Integer tempThr1;
    @ApiModelProperty(value = "阶段功率1")
    @TableField("heart_power1")
    private Integer heartPower1;
    @ApiModelProperty(value = "阶段温度阀值2")
    @TableField("temp_thr2")
    private Integer tempThr2;
    @ApiModelProperty(value = "阶段功率2")
    @TableField("heart_powe2")
    private Integer heartPowe2;
    @ApiModelProperty(value = "阶段功率3")
    @TableField("heart_powe3")
    private Integer heartPowe3;
    @ApiModelProperty(value = "阶段温度阀值3")
    @TableField("temp_thr3")
    private Integer tempThr3;
    @ApiModelProperty(value = "阶段功率4")
    @TableField("heart_powe4")
    private Integer heartPowe4;
    @ApiModelProperty(value = "阶段温度阀值4")
    @TableField("temp_thr4")
    private Integer tempThr4;
    @ApiModelProperty(value = "阶段功率5")
    @TableField("heart_powe5")
    private Integer heartPowe5;
    @ApiModelProperty(value = "阶段温度阀值5")
    @TableField("temp_thr5")
    private Integer tempThr5;
    @ApiModelProperty(value = "阶段温度阀值6")
    @TableField("temp_thr6")
    private Integer tempThr6;
    @ApiModelProperty(value = "阶段功率6")
    @TableField("heart_power6")
    private Integer heartPower6;
    @ApiModelProperty(value = "阶段温度阀值7")
    @TableField("temp_thr7")
    private Integer tempThr7;
    @ApiModelProperty(value = "阶段功率7")
    @TableField("heart_powe7")
    private Integer heartPowe7;
    @ApiModelProperty(value = "阶段功率8")
    @TableField("heart_powe8")
    private Integer heartPowe8;
    @ApiModelProperty(value = "阶段温度阀值8")
    @TableField("temp_thr8")
    private Integer tempThr8;
    @ApiModelProperty(value = "阶段功率9")
    @TableField("heart_powe9")
    private Integer heartPowe9;
    @ApiModelProperty(value = "阶段温度阀值9")
    @TableField("temp_thr9")
    private Integer tempThr9;
    @ApiModelProperty(value = "阶段功率10")
    @TableField("heart_powe10")
    private Integer heartPowe10;
    @ApiModelProperty(value = "阶段温度阀值10")
    @TableField("temp_thr10")
    private Integer tempThr10;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
