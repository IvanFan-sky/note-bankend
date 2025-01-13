package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.Album;
import com.spark.notebackend.model.dto.AlbumQueryDTO;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:27
 * @Version 1.0
 * @Description 相册服务接口
 */
public interface AlbumService extends IService<Album> {
    
    /**
     * 高级查询相册
     *
     * @param page     分页参数
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<Album> advancedQuery(Page<Album> page, AlbumQueryDTO queryDTO);

    /**
     * 查询用户可访问的相册列表
     *
     * @param userId 用户ID
     * @return 相册列表
     */
    List<Album> getAccessibleAlbums(Long userId);

    /**
     * 更新相册的标签
     *
     * @param albumId 相册ID
     * @param tagIds  标签ID列表
     */
    void setAlbumTags(Long albumId, List<Long> tagIds);

    /**
     * 更新相册的访问级别
     *
     * @param albumId     相册ID
     * @param accessLevel 新的访问级别
     * @return 是否更新成功
     */
    boolean updateAccessLevel(Long albumId, Integer accessLevel);

    /**
     * 更新相册的照片数量
     *
     * @param albumId 相册ID
     * @return 是否更新成功
     */
    boolean updatePhotoCount(Long albumId);

    /**
     * 验证用户是否有权限访问相册
     *
     * @param albumId 相册ID
     * @param userId  用户ID
     * @return 是否有权限
     */
    boolean hasPermission(Long albumId, Long userId);
} 