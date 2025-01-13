package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author spark
 * @Create 2025-01-13 20:02
 * @Version 1.0
 * @Description 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
} 