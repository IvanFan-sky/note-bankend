package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.Photo;
import com.spark.notebackend.model.dto.PhotoQueryDTO;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:28
 * @Version 1.0
 * @Description 照片服务接口
 */
public interface PhotoService extends IService<Photo> {
    
    /**
     * 高级查询照片
     *
     * @param page     分页参数
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<Photo> advancedQuery(Page<Photo> page, PhotoQueryDTO queryDTO);

    /**
     * 查询相册中的照片列表
     *
     * @param albumId 相册ID
     * @return 照片列表
     */
    List<Photo> getPhotosByAlbumId(Long albumId);

    /**
     * 更新照片的标签
     *
     * @param photoId 照片ID
     * @param tagIds  标签ID列表
     */
    void setPhotoTags(Long photoId, List<Long> tagIds);

    /**
     * 更新照片的排序序号
     *
     * @param photoId   照片ID
     * @param sortOrder 新的排序序号
     * @return 是否更新成功
     */
    boolean updateSortOrder(Long photoId, Integer sortOrder);

    /**
     * 批量更新照片的排序序号
     *
     * @param photoIds 照片ID列表
     * @return 是否更新成功
     */
    boolean batchUpdateSortOrder(List<Long> photoIds);
} 