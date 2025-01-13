package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.User;

/**
 * @Author spark
 * @Create 2025-01-13 20:03
 * @Version 1.0
 * @Description 用户服务接口
 */
public interface UserService extends IService<User> {
    /**
     * 分页查询用户
     *
     * @param page     分页参数
     * @param username 用户名(可选)
     * @param role     角色(可选)
     * @return 分页结果
     */
    Page<User> pageUsers(Page<User> page, String username, String role);
} 