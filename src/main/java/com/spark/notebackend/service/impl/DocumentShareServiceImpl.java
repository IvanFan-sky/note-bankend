package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.notebackend.common.api.ErrorCode;
import com.spark.notebackend.common.exception.BusinessException;
import com.spark.notebackend.entity.Document;
import com.spark.notebackend.entity.DocumentShare;
import com.spark.notebackend.entity.User;
import com.spark.notebackend.mapper.DocumentMapper;
import com.spark.notebackend.mapper.DocumentShareMapper;
import com.spark.notebackend.model.dto.DocumentShareDTO;
import com.spark.notebackend.model.vo.DocumentShareVO;
import com.spark.notebackend.service.DocumentShareService;
import com.spark.notebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author spark
 * @Create 2025-01-14 01:15
 * @Version 1.0
 * @Description 文档分享服务实现类
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "document_share")
public class DocumentShareServiceImpl extends ServiceImpl<DocumentShareMapper, DocumentShare> implements DocumentShareService {

    private final DocumentMapper documentMapper;
    private final UserService userService;

    @CacheEvict(value = {"document_share"}, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shareDocument(DocumentShareDTO shareDTO) {
        // 验证文档是否存在
        Document document = documentMapper.selectById(shareDTO.getDocumentId());
        if (document == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "文档不存在");
        }

        // 创建分享记录
        DocumentShare share = new DocumentShare();
        share.setDocumentId(shareDTO.getDocumentId());
        share.setFromUserId(shareDTO.getFromUserId());
        share.setToUserId(shareDTO.getToUserId());
        share.setShareType(shareDTO.getShareType());
        share.setSharePermission(shareDTO.getSharePermission());
        share.setShareStatus(0); // 初始状态：待接受
        share.setExpireTime(shareDTO.getExpireTime());

        this.save(share);
    }

    @Override
    public Page<DocumentShareVO> getSharesByUserId(Page<DocumentShare> page, Long userId, Integer shareType) {
        // 查询分享记录
        Page<DocumentShare> sharePage = this.page(page, new LambdaQueryWrapper<DocumentShare>()
                .eq(DocumentShare::getToUserId, userId)
                .eq(shareType != null, DocumentShare::getShareType, shareType)
                .orderByDesc(DocumentShare::getCreateTime));

        // 转换为VO
        Page<DocumentShareVO> voPage = new Page<>();
        BeanUtils.copyProperties(sharePage, voPage, "records");
        
        List<DocumentShareVO> voList = sharePage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public List<DocumentShareVO> getSharesByDocumentId(Long documentId) {
        List<DocumentShare> shares = this.list(new LambdaQueryWrapper<DocumentShare>()
                .eq(DocumentShare::getDocumentId, documentId));
        
        return shares.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = {"document_share"}, allEntries = true)
    @Override
    public boolean updateShareStatus(Long shareId, Integer shareStatus) {
        DocumentShare share = new DocumentShare();
        share.setId(shareId);
        share.setShareStatus(shareStatus);
        return this.updateById(share);
    }

    @Override
    public boolean hasSharePermission(Long documentId, Long userId) {
        return this.count(new LambdaQueryWrapper<DocumentShare>()
                .eq(DocumentShare::getDocumentId, documentId)
                .eq(DocumentShare::getToUserId, userId)
                .eq(DocumentShare::getShareStatus, 1)) > 0; // 已接受的分享
    }

    /**
     * 将DocumentShare转换为DocumentShareVO
     */
    private DocumentShareVO convertToVO(DocumentShare share) {
        DocumentShareVO vo = new DocumentShareVO();
        BeanUtils.copyProperties(share, vo);

        // 设置文档名称
        Document document = documentMapper.selectById(share.getDocumentId());
        if (document != null) {
            vo.setDocumentName(document.getDocName());
        }

        // 设置分享者名称
        User fromUser = userService.getById(share.getFromUserId());
        if (fromUser != null) {
            vo.setFromUsername(fromUser.getUsername());
        }

        // 设置接收者名称
        User toUser = userService.getById(share.getToUserId());
        if (toUser != null) {
            vo.setToUsername(toUser.getUsername());
        }

        return vo;
    }
} 