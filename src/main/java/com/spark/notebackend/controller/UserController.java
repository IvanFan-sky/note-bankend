package com.spark.notebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.notebackend.common.api.Result;
import com.spark.notebackend.entity.User;
import com.spark.notebackend.service.UserService;
import com.spark.notebackend.model.dto.UserDTO;
import com.spark.notebackend.model.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author spark
 * @Create 2025-01-13 20:05
 * @Version 1.0
 * @Description 用户控制器
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户管理接口")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/page")
    @ApiOperation("分页查询用户")
    public Result<Page<User>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") long current,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") long size,
            @ApiParam("用户名") @RequestParam(required = false) String username,
            @ApiParam("角色") @RequestParam(required = false) String role) {
        Page<User> page = new Page<>(current, size);
        return Result.success(userService.pageUsers(page, username, role));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询用户")
    public Result<UserVO> getById(@ApiParam("用户ID") @PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return Result.success(vo);
    }

    @PostMapping
    @ApiOperation("创建用户")
    public Result<Boolean> save(@RequestBody @Validated UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return Result.success(userService.save(user));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新用户")
    public Result<Boolean> update(
            @ApiParam("用户ID") @PathVariable Long id,
            @RequestBody @Validated UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setId(id);
        return Result.success(userService.updateById(user));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Result<Boolean> remove(@ApiParam("用户ID") @PathVariable Long id) {
        return Result.success(userService.removeById(id));
    }
} 