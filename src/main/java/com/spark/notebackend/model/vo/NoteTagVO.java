package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author spark
 * @Create 2025-01-13 22:30
 * @Version 1.0
 * @Description 笔记-标签关联视图对象
 */
@Data
@ApiModel(value = "NoteTagVO", description = "笔记-标签关联信息")
public class NoteTagVO {

    @ApiModelProperty("笔记ID")
    private Long noteId;

    @ApiModelProperty("标签ID")
    private Long tagId;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签颜色")
    private String color;
} 