package com.spark.notebackend.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author spark
 * @Create 2025-01-13 20:15
 * @Version 1.0
 * @Description 笔记实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_note")
@ApiModel(value = "Note对象", description = "笔记表")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("笔记标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("笔记内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("笔记摘要，选填")
    @TableField("summary")
    private String summary;

    @ApiModelProperty("笔记类型(0-普通笔记,1-Markdown)")
    @TableField("note_type")
    private Integer noteType;

    @ApiModelProperty("是否置顶(0-否,1-是)")
    @TableField("is_top")
    private Boolean isTop;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除标识(0-正常,1-已删除)")
    @TableLogic
    @TableField("deleted")
    private Boolean deleted;
} 