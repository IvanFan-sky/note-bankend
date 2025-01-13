package com.spark.notebackend.controller;

import com.spark.notebackend.common.api.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author spark
 * @Create 2025-01-13 19:10
 * @Version 1.0
 * @Description 测试控制器
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试控制器")
public class TestController {
    @GetMapping("/hello")
    @ApiOperation("hello")
    public Result<String> hello() {
        return Result.success("Hello, World!");
    }
}
 