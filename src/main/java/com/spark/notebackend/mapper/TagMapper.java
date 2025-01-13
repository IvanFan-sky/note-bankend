package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 20:31
 * @Version 1.0
 * @Description 标签Mapper接口
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    
    /**
     * 根据笔记ID查询关联的标签列表
     */
    @Select("SELECT t.* FROM t_tag t " +
            "INNER JOIN t_note_tag nt ON t.id = nt.tag_id " +
            "WHERE nt.note_id = #{noteId} AND t.deleted = 0")
    List<Tag> selectTagsByNoteId(@Param("noteId") Long noteId);

    /**
     * 根据文档ID查询关联的标签列表
     */
    @Select("SELECT t.* FROM t_tag t " +
            "INNER JOIN t_document_tag dt ON t.id = dt.tag_id " +
            "WHERE dt.document_id = #{documentId} AND t.deleted = 0")
    List<Tag> selectTagsByDocumentId(@Param("documentId") Long documentId);
} 