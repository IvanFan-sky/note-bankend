package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.Tag;

import java.util.List;

/**
 * @Author spark
 * @Create 2025-01-13 20:32
 * @Version 1.0
 * @Description 标签服务接口
 */
public interface TagService extends IService<Tag> {
    /**
     * 分页查询标签
     *
     * @param page     分页参数
     * @param userId   用户ID(可选)
     * @param tagName  标签名(可选)
     * @return 分页结果
     */
    Page<Tag> pageTags(Page<Tag> page, Long userId, String tagName);

    /**
     * 根据笔记ID查询关联的标签列表
     *
     * @param noteId 笔记ID
     * @return 标签列表
     */
    List<Tag> getTagsByNoteId(Long noteId);

    /**
     * 为笔记设置标签
     *
     * @param noteId 笔记ID
     * @param tagIds 标签ID列表
     */
    void setNoteTags(Long noteId, List<Long> tagIds);
} 