package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:21
 * @Version 1.0
 * @Description 照片Mapper接口
 */
@Mapper
public interface PhotoMapper extends BaseMapper<Photo> {
    
    /**
     * 根据标签ID查询关联的照片列表
     */
    @Select("SELECT p.* FROM t_photo p " +
            "INNER JOIN t_photo_tag pt ON p.id = pt.photo_id " +
            "WHERE pt.tag_id = #{tagId} AND p.deleted = 0")
    List<Photo> selectPhotosByTagId(@Param("tagId") Long tagId);

    /**
     * 查询相册中的照片列表
     */
    @Select("SELECT p.* FROM t_photo p " +
            "WHERE p.album_id = #{albumId} AND p.deleted = 0 " +
            "ORDER BY p.sort_order ASC, p.create_time DESC")
    List<Photo> selectPhotosByAlbumId(@Param("albumId") Long albumId);

    /**
     * 更新照片的排序序号
     */
    @Update("UPDATE t_photo SET sort_order = #{sortOrder} " +
            "WHERE id = #{photoId}")
    int updateSortOrder(@Param("photoId") Long photoId, @Param("sortOrder") Integer sortOrder);

    /**
     * 获取相册中最大的排序序号
     */
    @Select("SELECT MAX(sort_order) FROM t_photo " +
            "WHERE album_id = #{albumId} AND deleted = 0")
    Integer selectMaxSortOrder(@Param("albumId") Long albumId);
} 