package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.Album;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:20
 * @Version 1.0
 * @Description 相册Mapper接口
 */
@Mapper
public interface AlbumMapper extends BaseMapper<Album> {
    
    /**
     * 根据标签ID查询关联的相册列表
     */
    @Select("SELECT a.* FROM t_album a " +
            "INNER JOIN t_album_tag at ON a.id = at.album_id " +
            "WHERE at.tag_id = #{tagId} AND a.deleted = 0")
    List<Album> selectAlbumsByTagId(@Param("tagId") Long tagId);

    /**
     * 查询用户可访问的相册列表
     */
    @Select("SELECT DISTINCT a.* FROM t_album a " +
            "LEFT JOIN t_album_share as ON a.id = as.album_id " +
            "WHERE a.deleted = 0 AND (" +
            "   a.user_id = #{userId} OR " +  // 用户自己的相册
            "   as.user_id = #{userId} OR " +  // 共享给用户的相册
            "   a.access_level = 2" +          // 公开相册
            ")")
    List<Album> selectAccessibleAlbums(@Param("userId") Long userId);

    /**
     * 更新相册的照片数量
     */
    @Update("UPDATE t_album SET photo_count = (" +
            "   SELECT COUNT(*) FROM t_photo " +
            "   WHERE album_id = #{albumId} AND deleted = 0" +
            ") WHERE id = #{albumId}")
    int updatePhotoCount(@Param("albumId") Long albumId);
} 