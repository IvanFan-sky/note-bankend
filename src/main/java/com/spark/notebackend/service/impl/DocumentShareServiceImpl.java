package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author spark
 * @Create 2025-01-13 23:50
 * @Version 1.0
 * @Description 文档共享服务实现类
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

        // 删除原有的共享记录
        LambdaQueryWrapper<DocumentShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentShare::getDocumentId, shareDTO.getDocumentId())
               .in(DocumentShare::getUserId, shareDTO.getUserIds());
        this.remove(wrapper);

        // 创建新的共享记录
        List<DocumentShare> shares = shareDTO.getUserIds().stream()
                .map(userId -> {
                    DocumentShare share = new DocumentShare();
                    share.setDocumentId(shareDTO.getDocumentId());
                    share.setUserId(userId);
                    share.setPermission(shareDTO.getPermission());
                    return share;
                }).collect(Collectors.toList());

        // 批量保存共享记录
        this.saveBatch(shares);
    }

    @CacheEvict(value = {"document_share"}, allEntries = true)
    @Override
    public void cancelShare(Long documentId, Long userId) {
        LambdaQueryWrapper<DocumentShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentShare::getDocumentId, documentId)
               .eq(DocumentShare::getUserId, userId);
        this.remove(wrapper);
    }

    @Cacheable(key = "'doc_shares_' + #documentId")
    @Override
    public List<DocumentShareVO> getDocumentShares(Long documentId) {
        List<DocumentShare> shares = baseMapper.selectSharesByDocumentId(documentId);
        Document document = documentMapper.selectById(documentId);
        
        return shares.stream().map(share -> {
            DocumentShareVO vo = new DocumentShareVO();
            BeanUtils.copyProperties(share, vo);
            // 设置文档名称
            if (document != null) {
                vo.setDocName(document.getDocName());
            }
            // 设置用户名称
            User user = userService.getById(share.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
            }
            return vo;
        }).collect(Collectors.toList());
    }

    @Cacheable(key = "'user_shares_' + #userId")
    @Override
    public List<DocumentShareVO> getUserShares(Long userId) {
        List<DocumentShare> shares = baseMapper.selectSharesByUserId(userId);
        // 获取用户信息
        User user = userService.getById(userId);
        
        return shares.stream().map(share -> {
            DocumentShareVO vo = new DocumentShareVO();
            BeanUtils.copyProperties(share, vo);
            // 设置用户名称
            if (user != null) {
                vo.setUsername(user.getUsername());
            }
            // 设置文档名称
            Document document = documentMapper.selectById(share.getDocumentId());
            if (document != null) {
                vo.setDocName(document.getDocName());
            }
            return vo;
        }).collect(Collectors.toList());
    }

    @CacheEvict(value = {"document_share"}, allEntries = true)
    @Override
    public boolean updatePermission(Long documentId, Long userId, Integer permission) {
        LambdaUpdateWrapper<DocumentShare> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(DocumentShare::getDocumentId, documentId)
               .eq(DocumentShare::getUserId, userId)
               .set(DocumentShare::getPermission, permission);
        return this.update(wrapper);
    }

    @Override
    public boolean hasPermission(Long documentId, Long userId) {
        if (userId == null) {
            return false;
        }
        
        Document document = documentMapper.selectById(documentId);
        if (document == null) {
            return false;
        }
        
        // 文档所有者有权限
        if (document.getUserId().equals(userId)) {
            return true;
        }
        
        // 公开文档所有人都有权限
        if (document.getAccessLevel() == 2) {
            return true;
        }
        
        // 检查共享权限
        Integer permission = baseMapper.selectPermission(documentId, userId);
        return permission != null;
    }

    @Override
    public boolean hasEditPermission(Long documentId, Long userId) {
        if (userId == null) {
            return false;
        }
        
        Document document = documentMapper.selectById(documentId);
        if (document == null) {
            return false;
        }
        
        // 文档所有者有编辑权限
        if (document.getUserId().equals(userId)) {
            return true;
        }
        
        // 检查共享编辑权限
        Integer permission = baseMapper.selectPermission(documentId, userId);
        return permission != null && permission == 1;
    }
} 