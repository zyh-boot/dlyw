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
@TableName("a_account")
public class Account extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "公司名称")
    @TableField("org_name")
    private String orgName;
    @ApiModelProperty(value = "公司宗旨")
    @TableField("org_objective")
    private String orgObjective;
    @ApiModelProperty(value = "公司电话")
    @TableField("org_phone")
    private String orgPhone;
    @ApiModelProperty(value = "公司位置")
    @TableField("org_address")
    private String orgAddress;
    @ApiModelProperty(value = "公司联系人")
    @TableField("org_people")
    private String orgPeople;
    @ApiModelProperty(value = "客户电话")
    @TableField("kh_phone")
    private String khPhone;
    @ApiModelProperty(value = "客户名称")
    @TableField("kh_name")
    private String khName;
    @ApiModelProperty(value = "账号")
    @TableField("kh_account")
    private String khAccount;
    @ApiModelProperty(value = "重要程度(1重要2一般3一次)")
    @TableField("kh_degree")
    private Integer khDegree;
    @ApiModelProperty(value = "客户状态(1启用2暂停)")
    @TableField("kh_status")
    private Integer khStatus;
    @ApiModelProperty(value = "密码")
    @TableField("kh_pwd")
    private String khPwd;
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}
