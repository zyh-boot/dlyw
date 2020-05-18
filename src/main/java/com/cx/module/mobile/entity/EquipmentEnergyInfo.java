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
@TableName("a_equipment_energy_info")
public class EquipmentEnergyInfo extends BaseEnity implements Serializable {
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
    @ApiModelProperty(value = "A相电流")
    @TableField("current_a")
    private Float currentA;
    @ApiModelProperty(value = "B相电流")
    @TableField("current_b")
    private Float currentB;
    @ApiModelProperty(value = "C相电流")
    @TableField("current_c")
    private Float currentC;
    @ApiModelProperty(value = "B相电压")
    @TableField("voltage_b")
    private Float voltageB;
    @ApiModelProperty(value = "A相电压")
    @TableField("voltage_a")
    private Float voltageA;
    @ApiModelProperty(value = "C相电压")
    @TableField("voltage_c")
    private Float voltageC;
    @ApiModelProperty(value = "A相功率")
    @TableField("power_a")
    private Float powerA;
    @ApiModelProperty(value = "B相功率")
    @TableField("power_b")
    private Float powerB;
    @ApiModelProperty(value = "C相功率")
    @TableField("power_c")
    private Float powerC;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
