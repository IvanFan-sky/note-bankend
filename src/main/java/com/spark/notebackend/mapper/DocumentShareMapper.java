package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.DocumentShare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 23:47
 * @Version 1.0
 * @Description 文档共享Mapper接口
 */
@Mapper
public interface DocumentShareMapper extends BaseMapper<DocumentShare> {
    
    /**
     * 查询文档的共享用户列表
     */
    @Select("SELECT ds.* FROM t_document_share ds " +
            "WHERE ds.document_id = #{documentId} AND ds.deleted = 0")
    List<DocumentShare> selectSharesByDocumentId(@Param("documentId") Long documentId);

    /**
     * 查询用户的共享文档列表
     */
    @Select("SELECT ds.* FROM t_document_share ds " +
            "WHERE ds.user_id = #{userId} AND ds.deleted = 0")
    List<DocumentShare> selectSharesByUserId(@Param("userId") Long userId);

    /**
     * 查询用户对文档的共享权限
     */
    @Select("SELECT ds.permission FROM t_document_share ds " +
            "WHERE ds.document_id = #{documentId} " +
            "AND ds.user_id = #{userId} " +
            "AND ds.deleted = 0")
    Integer selectPermission(@Param("documentId") Long documentId, @Param("userId") Long userId);
} 