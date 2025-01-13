package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.NoteTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 22:01
 * @Version 1.0
 * @Description 笔记-标签关联Mapper接口
 */
@Mapper
public interface NoteTagMapper extends BaseMapper<NoteTag> {
    
    /**
     * 查询笔记的所有标签ID
     *
     * @param noteId 笔记ID
     * @return 标签ID列表
     */
    @Select("SELECT tag_id FROM t_note_tag WHERE note_id = #{noteId}")
    List<Long> selectTagIdsByNoteId(@Param("noteId") Long noteId);

    /**
     * 查询标签关联的所有笔记ID
     *
     * @param tagId 标签ID
     * @return 笔记ID列表
     */
    @Select("SELECT note_id FROM t_note_tag WHERE tag_id = #{tagId}")
    List<Long> selectNoteIdsByTagId(@Param("tagId") Long tagId);
} 