package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:15
 * @Version 1.0
 * @Description 相册视图对象
 */
@Data
@ApiModel(value = "AlbumVO", description = "相册信息返回对象")
public class AlbumVO {

    @ApiModelProperty("相册ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("相册名称")
    private String albumName;

    @ApiModelProperty("相册描述")
    private String description;

    @ApiModelProperty("封面图片URL")
    private String coverUrl;

    @ApiModelProperty("访问级别(0-私有,1-共享,2-公开)")
    private Integer accessLevel;

    @ApiModelProperty("照片数量")
    private Integer photoCount;

    @ApiModelProperty("关联的标签")
    private List<TagVO> tags;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
} 