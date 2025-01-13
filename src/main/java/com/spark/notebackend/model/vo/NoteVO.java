package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 21:03
 * @Version 1.0
 * @Description 笔记视图对象
 */
@Data
@ApiModel(value = "NoteVO", description = "笔记信息返回对象")
public class NoteVO {

    @ApiModelProperty("笔记ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记类型(0-普通笔记,1-Markdown)")
    private Integer noteType;

    @ApiModelProperty("是否置顶")
    private Boolean isTop;

    @ApiModelProperty("关联的标签")
    private List<TagVO> tags;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
} 