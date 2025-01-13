package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author spark
 * @Create 2025-01-14 01:25
 * @Version 1.0
 * @Description 文档分享数据传输对象
 */
@Data
@ApiModel(value = "DocumentShareDTO", description = "文档分享请求")
public class DocumentShareDTO {

    @ApiModelProperty("文档ID")
    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    @ApiModelProperty("分享者ID")
    @NotNull(message = "分享者ID不能为空")
    private Long fromUserId;

    @ApiModelProperty("接收者ID")
    @NotNull(message = "接收者ID不能为空")
    private Long toUserId;

    @ApiModelProperty("分享类型(0-个人,1-群组)")
    @NotNull(message = "分享类型不能为空")
    private Integer shareType;

    @ApiModelProperty("分享权限(0-只读,1-可编辑)")
    @NotNull(message = "分享权限不能为空")
    private Integer sharePermission;

    @ApiModelProperty("过期时间")
    @Future(message = "过期时间必须是将来时间")
    private LocalDateTime expireTime;
} 