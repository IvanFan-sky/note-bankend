package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.notebackend.entity.NoteTag;
import com.spark.notebackend.entity.Tag;
import com.spark.notebackend.mapper.NoteTagMapper;
import com.spark.notebackend.mapper.TagMapper;
import com.spark.notebackend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author spark
 * @Create 2025-01-13 20:33
 * @Version 1.0
 * @Description 标签服务实现类
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final NoteTagMapper noteTagMapper;

    /**
     * 分页查询标签列表
     * 支持按用户ID和标签名称进行筛选
     *
     * @param page    分页参数对象
     * @param userId  用户ID过滤条件（可选）
     * @param name 标签名称过滤条件（可选）
     * @return 分页查询结果
     */
    @Override
    public Page<Tag> pageTags(Page<Tag> page, Long userId, String name) {
        // 构建查询条件
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        
        // 按用户ID查询
        if (userId != null) {
            wrapper.eq(Tag::getUserId, userId);
        }
        // 按标签名称模糊查询
        if (StringUtils.hasText(name)) {
            wrapper.like(Tag::getName, name);
        }
        
        // 按创建时间降序排序
        wrapper.orderByDesc(Tag::getCreateTime);
        
        // 执行分页查询
        return this.page(page, wrapper);
    }

    /**
     * 获取笔记关联的标签列表
     *
     * @param noteId 笔记ID
     * @return 标签列表
     */
    @Override
    public List<Tag> getTagsByNoteId(Long noteId) {
        return baseMapper.selectTagsByNoteId(noteId);
    }

    /**
     * 获取文档关联的标签列表
     *
     * @param documentId 文档ID
     * @return 标签列表
     */
    @Override
    public List<Tag> getTagsByDocumentId(Long documentId) {
        return baseMapper.selectTagsByDocumentId(documentId);
    }

    /**
     * 设置笔记的标签关联关系
     * 该方法会先清除原有关联，再建立新的关联
     * 整个过程在一个事务中完成
     *
     * @param noteId 笔记ID
     * @param tagIds 标签ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setNoteTags(Long noteId, List<Long> tagIds) {
        // 删除原有的笔记-标签关联
        LambdaQueryWrapper<NoteTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NoteTag::getNoteId, noteId);
        noteTagMapper.delete(wrapper);
        
        // 如果没有新的标签，直接返回
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        
        // 创建新的笔记-标签关联
        List<NoteTag> noteTags = tagIds.stream()
                .map(tagId -> {
                    NoteTag noteTag = new NoteTag();
                    noteTag.setNoteId(noteId);
                    noteTag.setTagId(tagId);
                    return noteTag;
                }).collect(Collectors.toList());
        
        // 批量插入新的关联记录
        noteTags.forEach(noteTagMapper::insert);
    }
} 