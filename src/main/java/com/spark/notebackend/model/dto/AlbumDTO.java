package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author spark
 * @Create 2025-01-14 00:12
 * @Version 1.0
 * @Description 相册数据传输对象
 */
@Data
@ApiModel(value = "AlbumDTO", description = "相册创建/更新请求")
public class AlbumDTO {

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty("相册名称")
    @NotBlank(message = "相册名称不能为空")
    @Size(max = 50, message = "相册名称长度不能超过50个字符")
    private String albumName;

    @ApiModelProperty("相册描述")
    @Size(max = 200, message = "相册描述长度不能超过200个字符")
    private String description;

    @ApiModelProperty("封面图片URL")
    private String coverUrl;

    @ApiModelProperty("访问级别(0-私有,1-共享,2-公开)")
    @NotNull(message = "访问级别不能为空")
    private Integer accessLevel;
} 