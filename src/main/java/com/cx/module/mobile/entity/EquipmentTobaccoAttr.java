package com.cx.module.mobile.entity;

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
 * 烟炕设备属性 实体类
 *
 * @author admin
 * @Description Created on 2020-05-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("烟炕设备属性")
@TableName("a_equipment_tobacco_attr")
public class EquipmentTobaccoAttr extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备id")
    @TableField("equ_id")
    private Long equId;
    @ApiModelProperty(value = "设备编号")
    @TableField("code")
    private String code;
    @ApiModelProperty(value = "炒制编号")
    @TableField("period_no")
    private String periodNo;
    @ApiModelProperty(value = "风机电流")
    @TableField("fan_current")
    private Integer fanCurrent;
    @ApiModelProperty(value = "压缩机1电流")
    @TableField("compressor_current_one")
    private Integer compressorCurrentOne;
    @ApiModelProperty(value = "压缩机2电流")
    @TableField("compressor_current_two")
    private Integer compressorCurrentTwo;
    @ApiModelProperty(value = "系统电压")
    @TableField("system_voltage")
    private Integer systemVoltage;
    @ApiModelProperty(value = "上棚温度")
    @TableField("up_temperature")
    private Integer upTemperature;
    @ApiModelProperty(value = "上棚湿度")
    @TableField("up_humidity")
    private Integer upHumidity;
    @ApiModelProperty(value = "下棚温度")
    @TableField("down_temperature")
    private Integer downTemperature;
    @ApiModelProperty(value = "下棚湿度")
    @TableField("down_humidity")
    private Integer downHumidity;
    @ApiModelProperty(value = "管温1")
    @TableField("pipeline_temperature_one")
    private Integer pipelineTemperatureOne;
    @ApiModelProperty(value = "管温2")
    @TableField("pipeline_temperature_two")
    private Integer pipelineTemperatureTwo;
    @ApiModelProperty(value = "周期id")
    @TableField("work_period_id")
    private Long workPeriodId;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
