package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 01:01
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

    @ApiModelProperty("用户名称")
    private String username;

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

    @ApiModelProperty("访问级别(0-公开,1-私有,2-其他)")
    private Integer accessLevel;

    @ApiModelProperty("文档版本号")
    private Integer versionNum;

    @ApiModelProperty("关联的标签")
    private List<TagVO> tags;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 