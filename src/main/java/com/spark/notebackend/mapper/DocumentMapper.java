package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 23:10
 * @Version 1.0
 * @Description 文档Mapper接口
 */
@Mapper
public interface DocumentMapper extends BaseMapper<Document> {
    
    /**
     * 根据标签ID查询关联的文档列表
     */
    @Select("SELECT d.* FROM t_document d " +
            "INNER JOIN t_document_tag dt ON d.id = dt.document_id " +
            "WHERE dt.tag_id = #{tagId} AND d.deleted = 0")
    List<Document> selectDocumentsByTagId(@Param("tagId") Long tagId);

    /**
     * 查询用户可访问的文档列表
     * 包括：用户自己的文档、共享给用户的文档、公开文档
     */
    @Select("SELECT DISTINCT d.* FROM t_document d " +
            "LEFT JOIN t_document_share ds ON d.id = ds.document_id " +
            "WHERE d.deleted = 0 AND (" +
            "   d.user_id = #{userId} OR " +  // 用户自己的文档
            "   ds.user_id = #{userId} OR " +  // 共享给用户的文档
            "   d.access_level = 2" +          // 公开文档
            ")")
    List<Document> selectAccessibleDocuments(@Param("userId") Long userId);
} 