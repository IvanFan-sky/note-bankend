package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author spark
 * @Create 2025-01-13 23:46
 * @Version 1.0
 * @Description 文档共享视图对象
 */
@Data
@ApiModel(value = "DocumentShareVO", description = "文档共享信息")
public class DocumentShareVO {

    @ApiModelProperty("共享ID")
    private Long id;

    @ApiModelProperty("文档ID")
    private Long documentId;

    @ApiModelProperty("文档名称")
    private String docName;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("共享权限(0-只读,1-可编辑)")
    private Integer permission;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
} 