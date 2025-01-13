package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.Note;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author spark
 * @Create 2025-01-13 20:16
 * @Version 1.0
 * @Description 笔记Mapper接口
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {
} 