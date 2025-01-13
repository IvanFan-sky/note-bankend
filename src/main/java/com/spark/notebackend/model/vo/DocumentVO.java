package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 23:02
 * @Version 1.0
 * @Description 文档视图对象
 */
@Data
@ApiModel(value = "DocumentVO", description = "文档信息返回对象")
public class DocumentVO {

    @ApiModelProperty("文档ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("文档名称")
    private String docName;

    @ApiModelProperty("文档类型")
    private String docType;

    @ApiModelProperty("文档URL")
    private String docUrl;

    @ApiModelProperty("文档大小(字节)")
    private Long docSize;

    @ApiModelProperty("文档描述")
    private String description;

    @ApiModelProperty("访问级别(0-私有,1-共享,2-公开)")
    private Integer accessLevel;

    @ApiModelProperty("版本号")
    private Integer versionNum;

    @ApiModelProperty("关联的标签")
    private List<TagVO> tags;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
} 