package com.spark.notebackend.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author spark
 * @Create 2025-01-14 01:05
 * @Version 1.0
 * @Description 文档分享实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_document_share")
@ApiModel(value = "DocumentShare对象", description = "文档分享表")
public class DocumentShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文档ID")
    @TableField("document_id")
    private Long documentId;

    @ApiModelProperty("分享者ID")
    @TableField("from_user_id")
    private Long fromUserId;

    @ApiModelProperty("接收者ID")
    @TableField("to_user_id")
    private Long toUserId;

    @ApiModelProperty("分享类型(0-个人,1-群组)")
    @TableField("share_type")
    private Integer shareType;

    @ApiModelProperty("分享权限(0-只读,1-可编辑)")
    @TableField("share_permission")
    private Integer sharePermission;

    @ApiModelProperty("分享状态(0-待接受,1-已接受,2-已拒绝)")
    @TableField("share_status")
    private Integer shareStatus;

    @ApiModelProperty("分享时间")
    @TableField(value = "share_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shareTime;

    @ApiModelProperty("过期时间")
    @TableField("expire_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除标识(0-正常,1-已删除)")
    @TableLogic
    @TableField("deleted")
    private Boolean deleted;
} 