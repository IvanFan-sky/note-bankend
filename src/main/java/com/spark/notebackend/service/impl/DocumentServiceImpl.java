package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author spark
 * @Create 2025-01-13 23:12
 * @Version 1.0
 * @Description 文档服务实现类
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {

    private final DocumentTagMapper documentTagMapper;

    @Override
    public Page<Document> advancedQuery(Page<Document> page, DocumentQueryDTO queryDTO) {
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        
        // 基础条件查询
        if (queryDTO.getUserId() != null) {
            wrapper.eq(Document::getUserId, queryDTO.getUserId());
        }
        if (StringUtils.hasText(queryDTO.getDocName())) {
            wrapper.like(Document::getDocName, queryDTO.getDocName());
        }
        if (StringUtils.hasText(queryDTO.getDocType())) {
            wrapper.eq(Document::getDocType, queryDTO.getDocType());
        }
        if (queryDTO.getAccessLevel() != null) {
            wrapper.eq(Document::getAccessLevel, queryDTO.getAccessLevel());
        }
        
        // 标签过滤
        if (queryDTO.getTagId() != null) {
            List<Long> docIds = documentTagMapper.selectDocIdsByTagId(queryDTO.getTagId());
            if (docIds.isEmpty()) {
                return new Page<>();
            }
            wrapper.in(Document::getId, docIds);
        }
        
        // 排序处理
        String orderBy = queryDTO.getOrderBy();
        if ("update_time".equals(orderBy)) {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Document::getUpdateTime);
        } else {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Document::getCreateTime);
        }
        
        return this.page(page, wrapper);
    }

    @Override
    public List<Document> getAccessibleDocuments(Long userId) {
        return baseMapper.selectAccessibleDocuments(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDocumentTags(Long docId, List<Long> tagIds) {
        // 删除原有的文档-标签关联
        LambdaQueryWrapper<DocumentTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentTag::getDocumentId, docId);
        documentTagMapper.delete(wrapper);
        
        // 如果没有新的标签，直接返回
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        
        // 创建新的文档-标签关联
        List<DocumentTag> documentTags = tagIds.stream()
                .map(tagId -> {
                    DocumentTag documentTag = new DocumentTag();
                    documentTag.setDocumentId(docId);
                    documentTag.setTagId(tagId);
                    return documentTag;
                }).collect(Collectors.toList());
        
        // 批量插入新的关联记录
        documentTags.forEach(documentTagMapper::insert);
    }

    @Override
    public boolean updateAccessLevel(Long docId, Integer accessLevel) {
        Document doc = this.getById(docId);
        if (doc == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "文档不存在");
        }
        
        LambdaUpdateWrapper<Document> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Document::getId, docId)
               .set(Document::getAccessLevel, accessLevel);
        
        return this.update(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateVersion(Long docId, String docUrl, Long docSize) {
        Document doc = this.getById(docId);
        if (doc == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "文档不存在");
        }
        
        LambdaUpdateWrapper<Document> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Document::getId, docId)
               .set(Document::getDocUrl, docUrl)
               .set(Document::getDocSize, docSize)
               .set(Document::getVersionNum, doc.getVersionNum() + 1);
        
        return this.update(wrapper);
    }
} 