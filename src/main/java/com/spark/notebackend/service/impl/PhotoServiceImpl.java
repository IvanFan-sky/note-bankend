package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.notebackend.common.api.ErrorCode;
import com.spark.notebackend.common.exception.BusinessException;
import com.spark.notebackend.entity.Photo;
import com.spark.notebackend.entity.PhotoTag;
import com.spark.notebackend.mapper.PhotoMapper;
import com.spark.notebackend.mapper.PhotoTagMapper;
import com.spark.notebackend.model.dto.PhotoQueryDTO;
import com.spark.notebackend.service.AlbumService;
import com.spark.notebackend.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author spark
 * @Create 2025-01-14 00:35
 * @Version 1.0
 * @Description 照片服务实现类
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "photo")
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    private final PhotoTagMapper photoTagMapper;
    private final AlbumService albumService;

    @Override
    public Page<Photo> advancedQuery(Page<Photo> page, PhotoQueryDTO queryDTO) {
        LambdaQueryWrapper<Photo> wrapper = new LambdaQueryWrapper<>();
        
        // 基础条件查询
        if (queryDTO.getAlbumId() != null) {
            wrapper.eq(Photo::getAlbumId, queryDTO.getAlbumId());
        }
        if (StringUtils.hasText(queryDTO.getPhotoName())) {
            wrapper.like(Photo::getPhotoName, queryDTO.getPhotoName());
        }
        if (StringUtils.hasText(queryDTO.getPhotoType())) {
            wrapper.eq(Photo::getPhotoType, queryDTO.getPhotoType());
        }
        
        // 标签过滤
        if (queryDTO.getTagId() != null) {
            List<Long> photoIds = photoTagMapper.selectPhotoIdsByTagId(queryDTO.getTagId());
            if (photoIds.isEmpty()) {
                return new Page<>();
            }
            wrapper.in(Photo::getId, photoIds);
        }
        
        // 排序处理
        String orderBy = queryDTO.getOrderBy();
        if ("update_time".equals(orderBy)) {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Photo::getUpdateTime);
        } else if ("create_time".equals(orderBy)) {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Photo::getCreateTime);
        } else {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Photo::getSortOrder);
        }
        
        return this.page(page, wrapper);
    }

    @Cacheable(key = "'album_photos_' + #albumId")
    @Override
    public List<Photo> getPhotosByAlbumId(Long albumId) {
        return baseMapper.selectPhotosByAlbumId(albumId);
    }

    @CacheEvict(value = {"photo"}, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setPhotoTags(Long photoId, List<Long> tagIds) {
        // 验证照片是否存在
        Photo photo = this.getById(photoId);
        if (photo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "照片不存在");
        }

        // 删除原有的标签关联
        LambdaQueryWrapper<PhotoTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PhotoTag::getPhotoId, photoId);
        photoTagMapper.delete(wrapper);

        // 如果没有新的标签，直接返回
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }

        // 创建新的标签关联
        List<PhotoTag> photoTags = tagIds.stream()
                .map(tagId -> {
                    PhotoTag photoTag = new PhotoTag();
                    photoTag.setPhotoId(photoId);
                    photoTag.setTagId(tagId);
                    return photoTag;
                }).collect(Collectors.toList());

        // 批量保存标签关联
        photoTags.forEach(photoTagMapper::insert);
    }

    @CacheEvict(value = {"photo"}, allEntries = true)
    @Override
    public boolean updateSortOrder(Long photoId, Integer sortOrder) {
        return baseMapper.updateSortOrder(photoId, sortOrder) > 0;
    }

    @CacheEvict(value = {"photo"}, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateSortOrder(List<Long> photoIds) {
        if (photoIds == null || photoIds.isEmpty()) {
            return false;
        }

        // 按照传入的顺序更新排序号
        for (int i = 0; i < photoIds.size(); i++) {
            baseMapper.updateSortOrder(photoIds.get(i), i + 1);
        }
        return true;
    }
} 