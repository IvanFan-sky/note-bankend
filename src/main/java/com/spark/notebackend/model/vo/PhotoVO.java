package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:16
 * @Version 1.0
 * @Description 照片视图对象
 */
@Data
@ApiModel(value = "PhotoVO", description = "照片信息返回对象")
public class PhotoVO {

    @ApiModelProperty("照片ID")
    private Long id;

    @ApiModelProperty("相册ID")
    private Long albumId;

    @ApiModelProperty("相册名称")
    private String albumName;

    @ApiModelProperty("照片名称")
    private String photoName;

    @ApiModelProperty("照片URL")
    private String photoUrl;

    @ApiModelProperty("照片描述")
    private String description;

    @ApiModelProperty("照片大小(字节)")
    private Long photoSize;

    @ApiModelProperty("照片类型")
    private String photoType;

    @ApiModelProperty("排序序号")
    private Integer sortOrder;

    @ApiModelProperty("关联的标签")
    private List<TagVO> tags;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 