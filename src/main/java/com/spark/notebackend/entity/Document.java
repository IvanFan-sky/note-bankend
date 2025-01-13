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
 * @Create 2025-01-13 23:00
 * @Version 1.0
 * @Description 文档实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_document")
@ApiModel(value = "Document对象", description = "文档表")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("文档名称")
    @TableField("doc_name")
    private String docName;

    @ApiModelProperty("文档类型")
    @TableField("doc_type")
    private String docType;

    @ApiModelProperty("文档URL")
    @TableField("doc_url")
    private String docUrl;

    @ApiModelProperty("文档大小(字节)")
    @TableField("doc_size")
    private Long docSize;

    @ApiModelProperty("文档描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("访问级别(0-私有,1-共享,2-公开)")
    @TableField("access_level")
    private Integer accessLevel;

    @ApiModelProperty("版本号")
    @TableField("version_num")
    private Integer versionNum;

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