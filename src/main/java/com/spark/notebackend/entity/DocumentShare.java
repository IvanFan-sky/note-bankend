package com.spark.notebackend.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author spark
 * @Create 2025-01-13 23:40
 * @Version 1.0
 * @Description 文档共享实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_document_share")
@ApiModel(value = "DocumentShare对象", description = "文档共享表")
public class DocumentShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文档ID")
    @TableField("document_id")
    private Long documentId;

    @ApiModelProperty("被共享用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("共享权限(0-只读,1-可编辑)")
    @TableField("permission")
    private Integer permission;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除标识(0-正常,1-已删除)")
    @TableLogic
    @TableField("deleted")
    private Boolean deleted;
} 