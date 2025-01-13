package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.DocumentShare;
import com.spark.notebackend.model.dto.DocumentShareDTO;
import com.spark.notebackend.model.vo.DocumentShareVO;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 23:48
 * @Version 1.0
 * @Description 文档共享服务接口
 */
public interface DocumentShareService extends IService<DocumentShare> {

    /**
     * 共享文档给指定用户
     *
     * @param shareDTO 共享信息
     */
    void shareDocument(DocumentShareDTO shareDTO);

    /**
     * 取消文档共享
     *
     * @param documentId 文档ID
     * @param userId     用户ID
     */
    void cancelShare(Long documentId, Long userId);

    /**
     * 获取文档的共享用户列表
     *
     * @param documentId 文档ID
     * @return 共享信息列表
     */
    List<DocumentShareVO> getDocumentShares(Long documentId);

    /**
     * 获取用户的共享文档列表
     *
     * @param userId 用户ID
     * @return 共享信息列表
     */
    List<DocumentShareVO> getUserShares(Long userId);

    /**
     * 更新共享权限
     *
     * @param documentId 文档ID
     * @param userId     用户ID
     * @param permission 新的权限
     * @return 是否更新成功
     */
    boolean updatePermission(Long documentId, Long userId, Integer permission);

    /**
     * 验证用户是否有权限访问文档
     *
     * @param documentId 文档ID
     * @param userId     用户ID
     * @return 是否有权限
     */
    boolean hasPermission(Long documentId, Long userId);

    /**
     * 验证用户是否有编辑权限
     *
     * @param documentId 文档ID
     * @param userId     用户ID
     * @return 是否有编辑权限
     */
    boolean hasEditPermission(Long documentId, Long userId);
} 