package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author spark
 * @Create 2025-01-14 00:13
 * @Version 1.0
 * @Description 照片数据传输对象
 */
@Data
@ApiModel(value = "PhotoDTO", description = "照片创建/更新请求")
public class PhotoDTO {

    @ApiModelProperty("相册ID")
    @NotNull(message = "相册ID不能为空")
    private Long albumId;

    @ApiModelProperty("照片名称")
    @NotBlank(message = "照片名称不能为空")
    @Size(max = 100, message = "照片名称长度不能超过100个字符")
    private String photoName;

    @ApiModelProperty("照片URL")
    @NotBlank(message = "照片URL不能为空")
    private String photoUrl;

    @ApiModelProperty("照片描述")
    @Size(max = 200, message = "照片描述长度不能超过200个字符")
    private String description;

    @ApiModelProperty("照片大小(字节)")
    @NotNull(message = "照片大小不能为空")
    private Long photoSize;

    @ApiModelProperty("照片类型")
    @NotBlank(message = "照片类型不能为空")
    private String photoType;

    @ApiModelProperty("排序序号")
    private Integer sortOrder = 0;
} 