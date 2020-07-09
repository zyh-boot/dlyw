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
@TableName("a_equipment_work_period")
public class EquipmentWorkPeriod extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备code")
    @TableField("sb_code")
    private String sbCode;
    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "炒制时长")
    @TableField("work_sc")
    private Float workSc;
    @ApiModelProperty(value = "品种")
    @TableField("work_type")
    private Integer workType;
    @ApiModelProperty(value = "品种湿度")
    @TableField("work_sd")
    private Float workSd;
    @ApiModelProperty(value = "1.中度炒焦  2 轻微炒焦 3 正常  4 轻微湿  5中度湿")
    @TableField("sc")
    private Integer sc;
    @ApiModelProperty(value = "1.很难吃  2 难吃 3 好吃  4 很好吃  5超级好吃")
    @TableField("kg")
    private Integer kg;

    @ApiModelProperty(value = "是否结束（0结束 1开始）")
    @TableField("is_period")
    private Integer isPeriod;
    @ApiModelProperty(value = "是否评价（1未评价 2已评价）")
    @TableField("is_pj")
    private Integer isPj;
    @ApiModelProperty(value = "评价内容")
    @TableField("pj_content")
    private String pjContent;
    @ApiModelProperty(value = "炒制编号")
    @TableField("period_no")
    private String periodNo;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

}
