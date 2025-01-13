package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author spark
 * @Create 2025-01-13 22:02
 * @Version 1.0
 * @Description 笔记查询参数对象
 */
@Data
@ApiModel(value = "NoteQueryDTO", description = "笔记查询参数")
public class NoteQueryDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("标题关键字")
    private String title;

    @ApiModelProperty("笔记类型(0-普通笔记,1-Markdown)")
    private Integer noteType;

    @ApiModelProperty("标签ID")
    private Long tagId;

    @ApiModelProperty("是否只查询置顶笔记")
    private Boolean onlyTop;

    @ApiModelProperty("排序字段(create_time-创建时间/update_time-更新时间)")
    private String orderBy = "create_time";

    @ApiModelProperty("是否降序")
    private Boolean isDesc = true;
} 