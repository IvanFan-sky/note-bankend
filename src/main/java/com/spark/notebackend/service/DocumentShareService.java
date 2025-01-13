package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.DocumentShare;
import com.spark.notebackend.model.dto.DocumentShareDTO;
import com.spark.notebackend.model.vo.DocumentShareVO;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 01:20
 * @Version 1.0
 * @Description 文档分享服务接口
 */
public interface DocumentShareService extends IService<DocumentShare> {

    /**
     * 分享文档给指定用户
     *
     * @param shareDTO 分享信息
     */
    void shareDocument(DocumentShareDTO shareDTO);

    /**
     * 获取用户收到的分享
     *
     * @param page      分页参数
     * @param userId    用户ID
     * @param shareType 分享类型(0-个人,1-群组)
     * @return 分页结果
     */
    Page<DocumentShareVO> getSharesByUserId(Page<DocumentShare> page, Long userId, Integer shareType);

    /**
     * 获取文档的分享记录
     *
     * @param documentId 文档ID
     * @return 分享记录列表
     */
    List<DocumentShareVO> getSharesByDocumentId(Long documentId);

    /**
     * 更新分享状态
     *
     * @param shareId     分享ID
     * @param shareStatus 分享状态(0-待接受,1-已接受,2-已拒绝)
     * @return 是否更新成功
     */
    boolean updateShareStatus(Long shareId, Integer shareStatus);

    /**
     * 检查用户是否有文档的分享权限
     *
     * @param documentId 文档ID
     * @param userId     用户ID
     * @return 是否有权限
     */
    boolean hasSharePermission(Long documentId, Long userId);
} 