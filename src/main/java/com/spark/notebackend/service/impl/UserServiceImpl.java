package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.notebackend.entity.User;
import com.spark.notebackend.mapper.UserMapper;
import com.spark.notebackend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author spark
 * @Create 2025-01-13 20:04
 * @Version 1.0
 * @Description 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Page<User> pageUsers(Page<User> page, String username, String role) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (StringUtils.hasText(username)) {
            wrapper.like(User::getUsername, username);
        }
        if (StringUtils.hasText(role)) {
            wrapper.eq(User::getRole, role);
        }
        
        // 按创建时间降序排序
        wrapper.orderByDesc(User::getCreateTime);
        
        return this.page(page, wrapper);
    }
} 