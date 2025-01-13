package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.Document;
import com.spark.notebackend.model.dto.DocumentQueryDTO;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 23:11
 * @Version 1.0
 * @Description 文档服务接口
 */
public interface DocumentService extends IService<Document> {
    /**
     * 高级查询文档
     *
     * @param page     分页参数
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<Document> advancedQuery(Page<Document> page, DocumentQueryDTO queryDTO);

    /**
     * 查询用户可访问的文档列表
     *
     * @param userId 用户ID
     * @return 文档列表
     */
    List<Document> getAccessibleDocuments(Long userId);

    /**
     * 更新文档的标签
     *
     * @param docId  文档ID
     * @param tagIds 标签ID列表
     */
    void setDocumentTags(Long docId, List<Long> tagIds);

    /**
     * 更新文档的访问级别
     *
     * @param docId       文档ID
     * @param accessLevel 新的访问级别
     * @return 是否更新成功
     */
    boolean updateAccessLevel(Long docId, Integer accessLevel);

    /**
     * 更新文档版本
     *
     * @param docId      文档ID
     * @param docUrl     新的文档URL
     * @param docSize    新的文档大小
     * @return 是否更新成功
     */
    boolean updateVersion(Long docId, String docUrl, Long docSize);
} 