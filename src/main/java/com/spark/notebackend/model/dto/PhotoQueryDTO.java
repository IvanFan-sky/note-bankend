package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author spark
 * @Create 2025-01-14 00:18
 * @Version 1.0
 * @Description 照片查询参数对象
 */
@Data
@ApiModel(value = "PhotoQueryDTO", description = "照片查询参数")
public class PhotoQueryDTO {

    @ApiModelProperty("相册ID")
    private Long albumId;

    @ApiModelProperty("照片名称关键字")
    private String photoName;

    @ApiModelProperty("照片类型")
    private String photoType;

    @ApiModelProperty("标签ID")
    private Long tagId;

    @ApiModelProperty("排序字段(create_time-创建时间/update_time-更新时间/sort_order-排序序号)")
    private String orderBy = "sort_order";

    @ApiModelProperty("是否降序")
    private Boolean isDesc = false;
} 