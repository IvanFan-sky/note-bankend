package com.spark.notebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.notebackend.common.api.ErrorCode;
import com.spark.notebackend.common.exception.BusinessException;
import com.spark.notebackend.entity.Note;
import com.spark.notebackend.mapper.NoteMapper;
import com.spark.notebackend.service.NoteService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author spark
 * @Create 2025-01-13 20:18
 * @Version 1.0
 * @Description 笔记服务实现类
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Override
    public Page<Note> pageNotes(Page<Note> page, Long userId, String title, Integer noteType) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (userId != null) {
            wrapper.eq(Note::getUserId, userId);
        }
        if (StringUtils.hasText(title)) {
            wrapper.like(Note::getTitle, title);
        }
        if (noteType != null) {
            wrapper.eq(Note::getNoteType, noteType);
        }
        
        // 置顶笔记优先,再按创建时间降序
        wrapper.orderByDesc(Note::getIsTop)
               .orderByDesc(Note::getCreateTime);
        
        return this.page(page, wrapper);
    }

    @Override
    public boolean toggleTop(Long id) {
        Note note = this.getById(id);
        if (note == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "笔记不存在");
        }
        
        LambdaUpdateWrapper<Note> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Note::getId, id)
               .set(Note::getIsTop, !note.getIsTop());
        
        return this.update(wrapper);
    }
} 