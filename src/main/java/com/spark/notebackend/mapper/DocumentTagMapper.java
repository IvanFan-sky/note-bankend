package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.DocumentTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 23:21
 * @Version 1.0
 * @Description 文档-标签关联Mapper接口
 */
@Mapper
public interface DocumentTagMapper extends BaseMapper<DocumentTag> {
    
    /**
     * 根据标签ID查询关联的文档ID列表
     */
    @Select("SELECT document_id FROM t_document_tag WHERE tag_id = #{tagId}")
    List<Long> selectDocIdsByTagId(@Param("tagId") Long tagId);

    /**
     * 根据文档ID查询关联的标签ID列表
     */
    @Select("SELECT tag_id FROM t_document_tag WHERE document_id = #{documentId}")
    List<Long> selectTagIdsByDocumentId(@Param("documentId") Long documentId);
} 