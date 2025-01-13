package com.spark.notebackend.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @Author spark
 * @Create 2025-01-14 01:06
 * @Version 1.0
 * @Description 文档分享视图对象
 */
@Data
@ApiModel(value = "DocumentShareVO", description = "文档分享信息返回对象")
public class DocumentShareVO {

    @ApiModelProperty("分享ID")
    private Long id;

    @ApiModelProperty("文档ID")
    private Long documentId;

    @ApiModelProperty("文档名称")
    private String documentName;

    @ApiModelProperty("分享者ID")
    private Long fromUserId;

    @ApiModelProperty("分享者名称")
    private String fromUsername;

    @ApiModelProperty("接收者ID")
    private Long toUserId;

    @ApiModelProperty("接收者名称")
    private String toUsername;

    @ApiModelProperty("分享类型(0-个人,1-群组)")
    private Integer shareType;

    @ApiModelProperty("分享权限(0-只读,1-可编辑)")
    private Integer sharePermission;

    @ApiModelProperty("分享状态(0-待接受,1-已接受,2-已拒绝)")
    private Integer shareStatus;

    @ApiModelProperty("分享时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shareTime;

    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 