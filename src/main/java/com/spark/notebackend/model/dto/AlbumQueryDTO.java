package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author spark
 * @Create 2025-01-14 00:17
 * @Version 1.0
 * @Description 相册查询参数对象
 */
@Data
@ApiModel(value = "AlbumQueryDTO", description = "相册查询参数")
public class AlbumQueryDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("相册名称关键字")
    private String albumName;

    @ApiModelProperty("标签ID")
    private Long tagId;

    @ApiModelProperty("访问级别(0-私有,1-共享,2-公开)")
    private Integer accessLevel;

    @ApiModelProperty("排序字段(create_time-创建时间/update_time-更新时间/photo_count-照片数量)")
    private String orderBy = "create_time";

    @ApiModelProperty("是否降序")
    private Boolean isDesc = true;
} 