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

    /**
     * 分页查询笔记列表
     * 支持多条件组合查询，并按置顶状态和创建时间排序
     *
     * @param page     分页参数对象
     * @param userId   用户ID过滤条件（可选）
     * @param title    标题关键字过滤条件（可选）
     * @param noteType 笔记类型过滤条件（可选）
     * @return 分页查询结果
     */
    @Override
    public Page<Note> pageNotes(Page<Note> page, Long userId, String title, Integer noteType) {
        // 构建查询条件
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        
        // 按用户ID查询
        if (userId != null) {
            wrapper.eq(Note::getUserId, userId);
        }
        // 按标题模糊查询
        if (StringUtils.hasText(title)) {
            wrapper.like(Note::getTitle, title);
        }
        // 按笔记类型查询
        if (noteType != null) {
            wrapper.eq(Note::getNoteType, noteType);
        }
        
        // 排序规则：置顶优先，再按创建时间降序
        wrapper.orderByDesc(Note::getIsTop)
               .orderByDesc(Note::getCreateTime);
        
        // 执行分页查询
        return this.page(page, wrapper);
    }

    /**
     * 切换笔记的置顶状态
     * 如果笔记不存在会抛出业务异常
     *
     * @param id 笔记ID
     * @return 更新是否成功
     * @throws BusinessException 当笔记不存在时抛出
     */
    @Override
    public boolean toggleTop(Long id) {
        // 查询笔记是否存在
        Note note = this.getById(id);
        if (note == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "笔记不存在");
        }
        
        // 构建更新条件
        LambdaUpdateWrapper<Note> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Note::getId, id)
               // 将isTop设置为当前值的相反值
               .set(Note::getIsTop, !note.getIsTop());
        
        // 执行更新操作
        return this.update(wrapper);
    }
} 