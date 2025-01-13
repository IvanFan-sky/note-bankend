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
 * @Create 2025-01-14 00:10
 * @Version 1.0
 * @Description 相册实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_album")
@ApiModel(value = "Album对象", description = "相册表")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("相册名称")
    @TableField("album_name")
    private String albumName;

    @ApiModelProperty("相册描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("封面图片URL")
    @TableField("cover_url")
    private String coverUrl;

    @ApiModelProperty("访问级别(0-私有,1-共享,2-公开)")
    @TableField("access_level")
    private Integer accessLevel;

    @ApiModelProperty("照片数量")
    @TableField("photo_count")
    private Integer photoCount;

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