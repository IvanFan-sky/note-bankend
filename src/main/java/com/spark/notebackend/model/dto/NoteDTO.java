package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author spark
 * @Create 2025-01-13 21:02
 * @Version 1.0
 * @Description 笔记数据传输对象
 */
@Data
@ApiModel(value = "NoteDTO", description = "笔记创建/更新请求")
public class NoteDTO {

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty("笔记标题")
    @NotBlank(message = "笔记标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;

    @ApiModelProperty("笔记内容")
    @NotBlank(message = "笔记内容不能为空")
    private String content;

    @ApiModelProperty("笔记类型(0-普通笔记,1-Markdown)")
    @NotNull(message = "笔记类型不能为空")
    private Integer noteType;

    @ApiModelProperty("是否置顶")
    private Boolean isTop = false;
} 