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
 * @Description Created on 2020-05-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_send_command")
public class SendCommand extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备编号")
    @TableField("sb_code")
    private String sbCode;
    @ApiModelProperty(value = "PLC地址")
    @TableField("plc_address")
    private String plcAddress;
    @ApiModelProperty(value = "功能码")
    @TableField("function_code")
    private String functionCode;
    @ApiModelProperty(value = "起始地址")
    @TableField("qs_address")
    private String qsAddress;
    @ApiModelProperty(value = "寄存器数量")
    @TableField("jcqnum")
    private Integer jcqnum;
    @ApiModelProperty(value = "数据类型")
    @TableField("unit_type")
    private String unitType;
    @ApiModelProperty(value = "计算公式")
    @TableField("gs")
    private String gs;
    @ApiModelProperty(value = "发送内容")
    @TableField("command")
    private String command;
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
