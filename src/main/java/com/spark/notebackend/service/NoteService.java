package com.spark.notebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.notebackend.entity.Note;
import com.spark.notebackend.model.dto.NoteQueryDTO;

/**
 * @Author spark
 * @Create 2025-01-13 20:17
 * @Version 1.0
 * @Description 笔记服务接口
 */
public interface NoteService extends IService<Note> {
    /**
     * 分页查询笔记
     *
     * @param page     分页参数
     * @param userId   用户ID(可选)
     * @param title    标题关键字(可选)
     * @param noteType 笔记类型(可选)
     * @return 分页结果
     */
    Page<Note> pageNotes(Page<Note> page, Long userId, String title, Integer noteType);

    /**
     * 高级查询笔记
     *
     * @param page    分页参数
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<Note> advancedQuery(Page<Note> page, NoteQueryDTO queryDTO);

    /**
     * 切换笔记置顶状态
     *
     * @param id 笔记ID
     * @return 是否成功
     */
    boolean toggleTop(Long id);
} 