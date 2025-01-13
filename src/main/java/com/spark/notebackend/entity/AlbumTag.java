package com.spark.notebackend.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author spark
 * @Create 2025-01-14 00:22
 * @Version 1.0
 * @Description 相册-标签关联实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_album_tag")
@ApiModel(value = "AlbumTag对象", description = "相册-标签关联表")
public class AlbumTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("相册ID")
    @TableField("album_id")
    private Long albumId;

    @ApiModelProperty("标签ID")
    @TableField("tag_id")
    private Long tagId;
} 