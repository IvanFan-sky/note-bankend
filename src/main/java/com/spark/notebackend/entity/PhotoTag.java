package com.spark.notebackend.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author spark
 * @Create 2025-01-14 00:23
 * @Version 1.0
 * @Description 照片-标签关联实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_photo_tag")
@ApiModel(value = "PhotoTag对象", description = "照片-标签关联表")
public class PhotoTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("照片ID")
    @TableField("photo_id")
    private Long photoId;

    @ApiModelProperty("标签ID")
    @TableField("tag_id")
    private Long tagId;
} 