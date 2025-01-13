package com.spark.notebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.notebackend.entity.AlbumTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-14 00:25
 * @Version 1.0
 * @Description 相册-标签关联Mapper接口
 */
@Mapper
public interface AlbumTagMapper extends BaseMapper<AlbumTag> {
    
    /**
     * 查询相册关联的标签ID列表
     */
    @Select("SELECT tag_id FROM t_album_tag WHERE album_id = #{albumId}")
    List<Long> selectTagIdsByAlbumId(@Param("albumId") Long albumId);

    /**
     * 查询标签关联的相册ID列表
     */
    @Select("SELECT album_id FROM t_album_tag WHERE tag_id = #{tagId}")
    List<Long> selectAlbumIdsByTagId(@Param("tagId") Long tagId);
} 