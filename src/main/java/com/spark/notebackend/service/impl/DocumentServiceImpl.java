package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.notebackend.common.api.ErrorCode;
import com.spark.notebackend.common.exception.BusinessException;
import com.spark.notebackend.entity.Document;
import com.spark.notebackend.entity.DocumentTag;
import com.spark.notebackend.mapper.DocumentMapper;
import com.spark.notebackend.mapper.DocumentTagMapper;
import com.spark.notebackend.model.dto.DocumentQueryDTO;
import com.spark.notebackend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author spark
 * @Create 2025-01-14 01:10
 * @Version 1.0
 * @Description 文档服务实现类
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "document")
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {

    private final DocumentTagMapper documentTagMapper;

    @Override
    public Page<Document> advancedQuery(Page<Document> page, DocumentQueryDTO queryDTO) {
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        
        // 用户ID查询
        if (queryDTO.getUserId() != null) {
            wrapper.eq(Document::getUserId, queryDTO.getUserId());
        }
        
        // 文档名称模糊查询
        if (StringUtils.hasText(queryDTO.getDocName())) {
            wrapper.like(Document::getDocName, queryDTO.getDocName());
        }
        
        // 文档类型查询
        if (StringUtils.hasText(queryDTO.getDocType())) {
            wrapper.eq(Document::getDocType, queryDTO.getDocType());
        }
        
        // 访问级别查询
        if (queryDTO.getAccessLevel() != null) {
            wrapper.eq(Document::getAccessLevel, queryDTO.getAccessLevel());
        }
        
        // 按创建时间降序排序
        wrapper.orderByDesc(Document::getCreateTime);
        
        return this.page(page, wrapper);
    }

    @Override
    public List<Document> getAccessibleDocuments(Long userId) {
        return Collections.emptyList();
    }

    @Cacheable(key = "'doc_tags_' + #documentId")
    @Override
    public List<Long> getDocumentTagIds(Long documentId) {
        return documentTagMapper.selectTagIdsByDocumentId(documentId);
    }

    @CacheEvict(value = {"document"}, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDocumentTags(Long documentId, List<Long> tagIds) {
        // 验证文档是否存在
        Document document = this.getById(documentId);
        if (document == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "文档不存在");
        }

        // 删除原有的标签关联
        LambdaQueryWrapper<DocumentTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentTag::getDocumentId, documentId);
        documentTagMapper.delete(wrapper);

        // 如果没有新的标签，直接返回
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }

        // 创建新的标签关联
        List<DocumentTag> documentTags = tagIds.stream()
                .map(tagId -> {
                    DocumentTag documentTag = new DocumentTag();
                    documentTag.setDocumentId(documentId);
                    documentTag.setTagId(tagId);
                    return documentTag;
                }).collect(Collectors.toList());

        // 批量保存标签关联
        documentTags.forEach(documentTagMapper::insert);
    }

    @CacheEvict(value = {"document"}, allEntries = true)
    @Override
    public boolean updateAccessLevel(Long documentId, Integer accessLevel) {
        Document document = new Document();
        document.setId(documentId);
        document.setAccessLevel(accessLevel);
        return this.updateById(document);
    }

    @Override
    public boolean updateVersion(Long docId, String docUrl, Long docSize) {
        return false;
    }

    @Override
    public boolean hasPermission(Long documentId, Long userId) {
        if (userId == null) {
            return false;
        }

        Document document = this.getById(documentId);
        if (document == null) {
            return false;
        }

        // 文档所有者有权限
        if (document.getUserId().equals(userId)) {
            return true;
        }

        // 公开文档所有人都有权限
        if (document.getAccessLevel() == 0) {
            return true;
        }

        // TODO: 检查分享权限
        return false;
    }
} 