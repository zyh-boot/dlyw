package com.cx.module.mobile.entity;

import com.cx.common.base.entity.BaseEnity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
/**
 * 实体类
 * @author admin
 * @Description Created on 2020-05-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_equipment_heart_set_service")
public class EquipmentHeartSetService extends BaseEnity implements Serializable {
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
    @ApiModelProperty(value = "状态 0 关  1 开")
    @TableField("set_state")
    private Integer setState;
    @ApiModelProperty(value = "设置频率")
    @TableField("set_frequence")
    private Float setFrequence;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
