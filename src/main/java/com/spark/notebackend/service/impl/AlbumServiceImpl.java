package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.notebackend.common.api.ErrorCode;
import com.spark.notebackend.common.exception.BusinessException;
import com.spark.notebackend.entity.Album;
import com.spark.notebackend.entity.AlbumTag;
import com.spark.notebackend.mapper.AlbumMapper;
import com.spark.notebackend.mapper.AlbumTagMapper;
import com.spark.notebackend.model.dto.AlbumQueryDTO;
import com.spark.notebackend.service.AlbumService;
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
 * @Create 2025-01-14 00:30
 * @Version 1.0
 * @Description 相册服务实现类
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "album")
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    private final AlbumTagMapper albumTagMapper;

    @Override
    public Page<Album> advancedQuery(Page<Album> page, AlbumQueryDTO queryDTO) {
        LambdaQueryWrapper<Album> wrapper = new LambdaQueryWrapper<>();
        
        // 基础条件查询
        if (queryDTO.getUserId() != null) {
            wrapper.eq(Album::getUserId, queryDTO.getUserId());
        }
        if (StringUtils.hasText(queryDTO.getAlbumName())) {
            wrapper.like(Album::getAlbumName, queryDTO.getAlbumName());
        }
        if (queryDTO.getAccessLevel() != null) {
            wrapper.eq(Album::getAccessLevel, queryDTO.getAccessLevel());
        }
        
        // 标签过滤
        if (queryDTO.getTagId() != null) {
            List<Long> albumIds = albumTagMapper.selectAlbumIdsByTagId(queryDTO.getTagId());
            if (albumIds.isEmpty()) {
                return new Page<>();
            }
            wrapper.in(Album::getId, albumIds);
        }
        
        // 排序处理
        String orderBy = queryDTO.getOrderBy();
        if ("update_time".equals(orderBy)) {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Album::getUpdateTime);
        } else if ("photo_count".equals(orderBy)) {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Album::getPhotoCount);
        } else {
            wrapper.orderBy(true, queryDTO.getIsDesc(), Album::getCreateTime);
        }
        
        return this.page(page, wrapper);
    }

    @Cacheable(key = "'accessible_' + #userId")
    @Override
    public List<Album> getAccessibleAlbums(Long userId) {
        return baseMapper.selectAccessibleAlbums(userId);
    }

    @CacheEvict(value = {"album"}, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAlbumTags(Long albumId, List<Long> tagIds) {
        // 验证相册是否存在
        Album album = this.getById(albumId);
        if (album == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "相册不存在");
        }

        // 删除原有的标签关联
        LambdaQueryWrapper<AlbumTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AlbumTag::getAlbumId, albumId);
        albumTagMapper.delete(wrapper);

        // 如果没有新的标签，直接返回
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }

        // 创建新的标签关联
        List<AlbumTag> albumTags = tagIds.stream()
                .map(tagId -> {
                    AlbumTag albumTag = new AlbumTag();
                    albumTag.setAlbumId(albumId);
                    albumTag.setTagId(tagId);
                    return albumTag;
                }).collect(Collectors.toList());

        // 批量保存标签关联
        albumTags.forEach(albumTagMapper::insert);
    }

    @CacheEvict(value = {"album"}, allEntries = true)
    @Override
    public boolean updateAccessLevel(Long albumId, Integer accessLevel) {
        Album album = this.getById(albumId);
        if (album == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "相册不存在");
        }

        LambdaUpdateWrapper<Album> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Album::getId, albumId)
               .set(Album::getAccessLevel, accessLevel);

        return this.update(wrapper);
    }

    @CacheEvict(value = {"album"}, allEntries = true)
    @Override
    public boolean updatePhotoCount(Long albumId) {
        return baseMapper.updatePhotoCount(albumId) > 0;
    }

    @Override
    public boolean hasPermission(Long albumId, Long userId) {
        if (userId == null) {
            return false;
        }

        Album album = this.getById(albumId);
        if (album == null) {
            return false;
        }

        // 相册所有者有权限
        if (album.getUserId().equals(userId)) {
            return true;
        }

        // 公开相册所有人都有权限
        if (album.getAccessLevel() == 2) {
            return true;
        }

        // TODO: 检查共享权限
        return false;
    }
} 