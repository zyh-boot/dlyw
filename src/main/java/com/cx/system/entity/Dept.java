package com.cx.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cx.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator·
 */
@Data
@TableName("t_dept")
@ApiModel("部门信息表")
@Excel("部门信息表")
public class Dept implements Serializable {

    /**
     * * LEVEL机构等级 机构公司 level 0或者空 ,厂区level 1, 部门level ,2 车间level 3, 班组level 4
     */
    public static final int LEVEL_0 = 0;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;
    public static final int LEVEL_4 = 4;

    private static final long serialVersionUID = 5702271568363798328L;
    /**
     * 部门 ID
     */
    @ApiModelProperty("部门 ID")
    @TableId(value = "DEPT_ID", type = IdType.AUTO)
    private Long deptId;

    /**
     * 上级部门 ID
     */
    @ApiModelProperty("上级部门 ID")
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @TableField("DEPT_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ExcelField(value = "部门名称")
    private String deptName;

    /**
     * 部门名称
     */
    @TableField("DEPT_CODE")
    private String deptCode;

    /**
     * 同步数据---部门id
     */
    @TableField("socure_id")
    private String socureId;
    /**
     * *等级
     */
    @TableField("level")
    private Integer level;
    /**
     * 排序
     */
    @TableField("ORDER_NUM")
    private Long orderNum;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

}
