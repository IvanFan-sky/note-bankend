package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.PhotoTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:26
 * @Version 1.0
 * @Description 照片-标签关联Mapper接口
 */
@Mapper
public interface PhotoTagMapper extends BaseMapper<PhotoTag> {
    
    /**
     * 查询照片关联的标签ID列表
     */
    @Select("SELECT tag_id FROM t_photo_tag WHERE photo_id = #{photoId}")
    List<Long> selectTagIdsByPhotoId(@Param("photoId") Long photoId);

    /**
     * 查询标签关联的照片ID列表
     */
    @Select("SELECT photo_id FROM t_photo_tag WHERE tag_id = #{tagId}")
    List<Long> selectPhotoIdsByTagId(@Param("tagId") Long tagId);
} 