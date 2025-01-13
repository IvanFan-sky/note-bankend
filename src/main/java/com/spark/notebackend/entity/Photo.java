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
 * @Create 2025-01-14 00:11
 * @Version 1.0
 * @Description 照片实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_photo")
@ApiModel(value = "Photo对象", description = "照片表")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("相册ID")
    @TableField("album_id")
    private Long albumId;

    @ApiModelProperty("照片名称")
    @TableField("photo_name")
    private String photoName;

    @ApiModelProperty("照片URL")
    @TableField("photo_url")
    private String photoUrl;

    @ApiModelProperty("照片描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("照片大小(字节)")
    @TableField("photo_size")
    private Long photoSize;

    @ApiModelProperty("照片类型")
    @TableField("photo_type")
    private String photoType;

    @ApiModelProperty("排序序号")
    @TableField("sort_order")
    private Integer sortOrder;

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