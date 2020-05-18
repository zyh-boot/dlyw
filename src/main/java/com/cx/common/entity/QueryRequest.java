package com.cx.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Administrator·
 */
@Data
@ToString
@ApiModel(description = "分页")
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    /**
     * 当前页面数据量
     */
    @ApiModelProperty("条数")
    private int pageSize;
    /**
     * 当前页码
     */
    @ApiModelProperty("页码")
    private int pageNum;
    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    @ApiModelProperty("排序规则")
    private String order;
}
