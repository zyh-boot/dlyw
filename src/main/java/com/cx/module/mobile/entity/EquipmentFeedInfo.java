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
@TableName("a_equipment_feed_info")
public class EquipmentFeedInfo extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备code")
    @TableField("sb_code")
    private String sbCode;
    @ApiModelProperty(value = "进料状态 0 未上电 1 运行 2 停止")
    @TableField("run_state")
    private Integer runState;
    @ApiModelProperty(value = "运行频率")
    @TableField("run_frequence")
    private Float runFrequence;
    @ApiModelProperty(value = "输出功率")
    @TableField("out_power")
    private Float outPower;
    @ApiModelProperty(value = "炒货周期")
    @TableField("period_id")
    private Long periodId;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
