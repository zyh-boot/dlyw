package com.cx.module.myUser.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.cx.common.base.entity.BaseEnity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表 实体类
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户表")
@TableName("t_user")
public class TUser extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户ID")
    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Long userId;
    @ApiModelProperty(value = "用户名")
    @TableField("USERNAME")
    private String username;
    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;
    @ApiModelProperty(value = "部门ID")
    @TableField("DEPT_ID")
    private Long deptId;
    @ApiModelProperty(value = "部门名称")
    @TableField("dept_name")
    private String deptName;
    @ApiModelProperty(value = "邮箱")
    @TableField("EMAIL")
    private String email;
    @ApiModelProperty(value = "联系电话")
    @TableField("MOBILE")
    private String mobile;
    @ApiModelProperty(value = "身份证号")
    @TableField("idcard_num")
    private Long idcardNum;
    @ApiModelProperty(value = "微信号")
    @TableField("wechart")
    private String wechart;
    @ApiModelProperty(value = "账户剩余时间")
    @TableField("remaing")
    private LocalDateTime remaing;
    @ApiModelProperty(value = "用户姓名")
    @TableField("account_name")
    private String accountName;
    @ApiModelProperty(value = "状态 0锁定 1有效")
    @TableField("STATUS")
    private String status;


    @ApiModelProperty(value = "帐号开通时间")
    @TableField("open")
    private LocalDateTime open;


    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_TIME")
    private LocalDateTime modifyTime;


    @ApiModelProperty(value = "最近访问时间")
    @TableField("LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;
    @ApiModelProperty(value = "性别 0男 1女 2保密")
    @TableField("SSEX")
    private String ssex;
    @ApiModelProperty(value = "是否开启tab，0关闭 1开启")
    @TableField("IS_TAB")
    private String isTab;
    @ApiModelProperty(value = "主题")
    @TableField("THEME")
    private String theme;
    @ApiModelProperty(value = "头像")
    @TableField("AVATAR")
    private String avatar;
    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
