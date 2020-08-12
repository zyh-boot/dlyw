package com.cx.module.amyequipment.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cx.common.base.entity.BaseEnity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 存放设备相关数据 实体类
 *
 * @author admin
 * @Description Created on 2020-08-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("存放设备相关数据")
@TableName("a_myequipment")
public class Myequipment extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "设备主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备名")
    @TableField("eq_name")
    private String eqName;
    @ApiModelProperty(value = "设备编号")
    @TableField("eq_code")
    private Long eqCode;
    @ApiModelProperty(value = "机构ID")
    @TableField("eq_dept_id")
    private Long eqDeptId;
    @ApiModelProperty(value = "机构名")
    @TableField("eq_dept_name")
    private String eqDeptName;
    @ApiModelProperty(value = "机构类别")
    @TableField("eq_dept_category")
    private String eqDeptCategory;
    @ApiModelProperty(value = "设备类型")
    @TableField("eq_type")
    private Integer eqType;
    @ApiModelProperty(value = "设备台账")
    @TableField("eq_ledger")
    private String eqLedger;
    @ApiModelProperty(value = "PM2.5")
    @TableField("eq_pm_two")
    private BigDecimal eqPmTwo;
    @ApiModelProperty(value = "PM10")
    @TableField("eq_pm_ten")
    private BigDecimal eqPmTen;
    @ApiModelProperty(value = "CO(一氧化碳)")
    @TableField("eq_co")
    private BigDecimal eqCo;
    @ApiModelProperty(value = "SO2(二氧化硫)")
    @TableField("eq_so2")
    private BigDecimal eqSo2;
    @ApiModelProperty(value = "NO2(二氧化氮)")
    @TableField("sq_no2")
    private BigDecimal sqNo2;
    @ApiModelProperty(value = "O3(臭氧)")
    @TableField("sq_o3")
    private BigDecimal sqO3;
    @ApiModelProperty(value = "设备地址")
    @TableField("eq_address")
    private String eqAddress;

    @ApiModelProperty(value = "设备添加时间")
    @TableField(value = "eq_add_time")
    private LocalDateTime eqAddTime;
    @ApiModelProperty(value = "设备供应商")
    @TableField("eq_supplier")
    private String eqSupplier;
    @ApiModelProperty(value = "是否检修(1:正常 0:修理)")
    @TableField("eq_overhaul")
    private Integer eqOverhaul;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;
}
