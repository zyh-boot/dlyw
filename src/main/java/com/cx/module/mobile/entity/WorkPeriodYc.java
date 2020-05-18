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
 * @author admin
 * @Description Created on 2020-05-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@TableName("a_work_period_yc")
public class WorkPeriodYc extends BaseEnity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "设备code")
    @TableField("sb_code")
    private String sbCode;
    @ApiModelProperty(value = "炒制周期")
    @TableField("period_id")
    private Integer periodId;
    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "故障种类 1滚筒错误 2加热故障 3进料错误")
    @TableField("yc_type")
    private Integer ycType;
    @ApiModelProperty(value = "状态")
    @TableField("yc_status")
    private Integer ycStatus;
    @ApiModelProperty(value = "是否处理")
    @TableField("is_deal")
    private Integer isDeal;
    @ApiModelProperty(value = "故障内容")
    @TableField("yc_info")
    private String ycInfo;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}
