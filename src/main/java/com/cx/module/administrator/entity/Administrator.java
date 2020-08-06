package com.cx.module.administrator.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cx.common.base.entity.BaseEnity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_administrator")
public class Administrator extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "管理员表主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "管理员姓名")
    @TableField("admin_name")
    private String adminName;
    @ApiModelProperty(value = "所属机构ID")
    @TableField("dept_id")
    private Integer deptId;
    @ApiModelProperty(value = "所属机构名")
    @TableField("dept_name")
    private String deptName;
    @ApiModelProperty(value = "身份证号")
    @TableField("idcard_num")
    private String idcardNum;
    @ApiModelProperty(value = "登录账号")
    @TableField("account_num")
    private String accountNum;
    @ApiModelProperty(value = "登录密码")
    @TableField("account_pwd")
    private String accountPwd;
    @ApiModelProperty(value = "电话")
    @TableField("phone_num")
    private String phoneNum;
    @ApiModelProperty(value = "微信号")
    @TableField("wechart_num")
    private String wechartNum;

    @ApiModelProperty(value = "账户剩余时间")
    @TableField("remaing_time")
    private LocalDateTime remaingTime;

    @ApiModelProperty(value = "账户开通时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;


    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
