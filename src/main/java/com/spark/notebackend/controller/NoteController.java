package com.spark.notebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.notebackend.common.api.Result;
import com.spark.notebackend.entity.Note;
import com.spark.notebackend.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author spark
 * @Create 2025-01-13 20:19
 * @Version 1.0
 * @Description 笔记控制器
 */
@RestController
@RequestMapping("/notes")
@Api(tags = "笔记管理接口")
@RequiredArgsConstructor
@Validated
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/page")
    @ApiOperation("分页查询笔记")
    public Result<Page<Note>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") long current,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") long size,
            @ApiParam("用户ID") @RequestParam(required = false) Long userId,
            @ApiParam("标题关键字") @RequestParam(required = false) String title,
            @ApiParam("笔记类型") @RequestParam(required = false) Integer noteType) {
        Page<Note> page = new Page<>(current, size);
        return Result.success(noteService.pageNotes(page, userId, title, noteType));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询笔记")
    public Result<Note> getById(@ApiParam("笔记ID") @PathVariable Long id) {
        return Result.success(noteService.getById(id));
    }

    @PostMapping
    @ApiOperation("创建笔记")
    public Result<Boolean> save(@RequestBody @Validated Note note) {
        return Result.success(noteService.save(note));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新笔记")
    public Result<Boolean> update(
            @ApiParam("笔记ID") @PathVariable Long id,
            @RequestBody @Validated Note note) {
        note.setId(id);
        return Result.success(noteService.updateById(note));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除笔记")
    public Result<Boolean> remove(@ApiParam("笔记ID") @PathVariable Long id) {
        return Result.success(noteService.removeById(id));
    }

    @PutMapping("/{id}/top")
    @ApiOperation("切换笔记置顶状态")
    public Result<Boolean> toggleTop(@ApiParam("笔记ID") @PathVariable Long id) {
        return Result.success(noteService.toggleTop(id));
    }
} 