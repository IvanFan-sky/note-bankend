package com.spark.notebackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author spark
 * @Create 2025-01-13 22:00
 * @Version 1.0
 * @Description 笔记-标签关联实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_note_tag")
@ApiModel(value = "NoteTag对象", description = "笔记-标签关联表")
public class NoteTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("笔记ID")
    @TableField("note_id")
    private Long noteId;

    @ApiModelProperty("标签ID")
    @TableField("tag_id")
    private Long tagId;
} 