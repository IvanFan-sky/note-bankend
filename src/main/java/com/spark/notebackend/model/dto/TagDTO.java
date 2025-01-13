package com.spark.notebackend.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author spark
 * @Create 2025-01-13 21:04
 * @Version 1.0
 * @Description 标签数据传输对象
 */
@Data
@ApiModel(value = "TagDTO", description = "标签创建/更新请求")
public class TagDTO {

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty("标签名称")
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 20, message = "标签名称长度不能超过20个字符")
    private String name;

    @ApiModelProperty("标签颜色")
    @Pattern(regexp = "^#[0-9a-fA-F]{6}$", message = "颜色格式必须是十六进制颜色码，如#FF0000")
    private String color;
} 