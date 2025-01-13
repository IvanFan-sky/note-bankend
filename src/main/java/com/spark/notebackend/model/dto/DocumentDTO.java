package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author spark
 * @Create 2025-01-13 23:01
 * @Version 1.0
 * @Description 文档数据传输对象
 */
@Data
@ApiModel(value = "DocumentDTO", description = "文档创建/更新请求")
public class DocumentDTO {

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty("文档名称")
    @NotBlank(message = "文档名称不能为空")
    @Size(max = 100, message = "文档名称长度不能超过100个字符")
    private String docName;

    @ApiModelProperty("文档类型")
    @NotBlank(message = "文档类型不能为空")
    private String docType;

    @ApiModelProperty("文档URL")
    @NotBlank(message = "文档URL不能为空")
    private String docUrl;

    @ApiModelProperty("文档大小(字节)")
    @NotNull(message = "文档大小不能为空")
    private Long docSize;

    @ApiModelProperty("文档描述")
    @Size(max = 500, message = "文档描述长度不能超过500个字符")
    private String description;

    @ApiModelProperty("访问级别(0-私有,1-共享,2-公开)")
    @NotNull(message = "访问级别不能为空")
    private Integer accessLevel;

    @ApiModelProperty("版本号")
    private Integer versionNum = 1;
} 