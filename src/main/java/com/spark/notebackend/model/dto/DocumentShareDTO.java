package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 23:45
 * @Version 1.0
 * @Description 文档共享请求对象
 */
@Data
@ApiModel(value = "DocumentShareDTO", description = "文档共享请求")
public class DocumentShareDTO {

    @ApiModelProperty("文档ID")
    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    @ApiModelProperty("共享用户ID列表")
    @NotNull(message = "共享用户不能为空")
    private List<Long> userIds;

    @ApiModelProperty("共享权限(0-只读,1-可编辑)")
    @NotNull(message = "共享权限不能为空")
    private Integer permission;
} 