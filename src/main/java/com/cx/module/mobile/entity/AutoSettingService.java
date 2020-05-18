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
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_auto_setting_service")
public class AutoSettingService extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备code")
    @TableField("sb_code")
    private String sbCode;
    @ApiModelProperty(value = "加热停止后多长时间进料停止")
    @TableField("heart_stop_feed_s")
    private Integer heartStopFeedS;
    @ApiModelProperty(value = "过温保护")
    @TableField("over_temp_protect")
    private Integer overTempProtect;
    @ApiModelProperty(value = "提前进料温度点")
    @TableField("pre_feed_temp")
    private Integer preFeedTemp;
    @ApiModelProperty(value = "PID   P常数")
    @TableField("kp")
    private Float kp;
    @ApiModelProperty(value = "PID   I常数")
    @TableField("ki")
    private Float ki;
    @ApiModelProperty(value = "PID   D常数")
    @TableField("kd")
    private Float kd;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
