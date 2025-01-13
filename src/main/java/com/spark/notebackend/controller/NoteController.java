package com.spark.notebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.notebackend.common.api.Result;
import com.spark.notebackend.entity.Note;
import com.spark.notebackend.model.dto.NoteDTO;
import com.spark.notebackend.model.dto.NoteQueryDTO;
import com.spark.notebackend.model.vo.NoteVO;
import com.spark.notebackend.model.vo.TagVO;
import com.spark.notebackend.service.NoteService;
import com.spark.notebackend.service.TagService;
import com.spark.notebackend.util.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    private final TagService tagService;

    /**
     * 分页查询笔记列表
     * 支持按用户ID、标题关键字、笔记类型进行筛选
     * 查询结果会包含每个笔记关联的标签信息
     *
     * @param current  当前页码，默认1
     * @param size     每页大小，默认10
     * @param userId   用户ID过滤条件（可选）
     * @param title    标题关键字过滤条件（可选）
     * @param noteType 笔记类型过滤条件（可选）
     * @return 分页结果，包含笔记详细信息
     */
    @GetMapping("/page")
    @ApiOperation("分页查询笔记")
    public Result<Page<NoteVO>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") long current,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") long size,
            @ApiParam("用户ID") @RequestParam(required = false) Long userId,
            @ApiParam("标题关键字") @RequestParam(required = false) String title,
            @ApiParam("笔记类型") @RequestParam(required = false) Integer noteType) {
        Page<Note> page = new Page<>(current, size);
        Page<Note> notePage = noteService.pageNotes(page, userId, title, noteType);
        
        // 将实体对象转换为VO对象
        Page<NoteVO> voPage = BeanCopyUtils.copyBeanPage(notePage, NoteVO.class);
        // 为每个笔记加载关联的标签信息
        voPage.getRecords().forEach(vo -> 
            vo.setTags(BeanCopyUtils.copyBeanList(
                tagService.getTagsByNoteId(vo.getId()), 
                TagVO.class
            ))
        );
        
        return Result.success(voPage);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询笔记")
    public Result<NoteVO> getById(@ApiParam("笔记ID") @PathVariable Long id) {
        Note note = noteService.getById(id);
        if (note == null) {
            return Result.fail("笔记不存在");
        }
        NoteVO vo = BeanCopyUtils.copyBean(note, NoteVO.class);
        vo.setTags(BeanCopyUtils.copyBeanList(
            tagService.getTagsByNoteId(id),
            TagVO.class
        ));
        return Result.success(vo);
    }

    @PostMapping
    @ApiOperation("创建笔记")
    public Result<Boolean> save(@RequestBody @Validated NoteDTO noteDTO) {
        Note note = new Note();
        BeanUtils.copyProperties(noteDTO, note);
        return Result.success(noteService.save(note));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新笔记")
    public Result<Boolean> update(
            @ApiParam("笔记ID") @PathVariable Long id,
            @RequestBody @Validated NoteDTO noteDTO) {
        Note note = new Note();
        BeanUtils.copyProperties(noteDTO, note);
        note.setId(id);
        return Result.success(noteService.updateById(note));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除笔记")
    public Result<Boolean> remove(@ApiParam("笔记ID") @PathVariable Long id) {
        return Result.success(noteService.removeById(id));
    }

    /**
     * 切换笔记的置顶状态
     * 如果笔记当前是置顶状态，则取消置顶
     * 如果笔记当前是非置顶状态，则设为置顶
     *
     * @param id 笔记ID
     * @return 操作结果
     */
    @PutMapping("/{id}/top")
    @ApiOperation("切换笔记置顶状态")
    public Result<Boolean> toggleTop(@ApiParam("笔记ID") @PathVariable Long id) {
        return Result.success(noteService.toggleTop(id));
    }

    /**
     * 高级查询笔记
     * 支持多条件组合查询、标签过滤、自定义排序等
     *
     * @param current  当前页码
     * @param size     每页大小
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    @PostMapping("/query")
    @ApiOperation("高级查询笔记")
    public Result<Page<NoteVO>> advancedQuery(
            @ApiParam("页码") @RequestParam(defaultValue = "1") long current,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") long size,
            @RequestBody NoteQueryDTO queryDTO) {
        Page<Note> page = new Page<>(current, size);
        Page<Note> notePage = noteService.advancedQuery(page, queryDTO);
        
        // 转换为VO对象
        Page<NoteVO> voPage = BeanCopyUtils.copyBeanPage(notePage, NoteVO.class);
        // 为每个笔记加载关联的标签信息
        voPage.getRecords().forEach(vo -> 
            vo.setTags(BeanCopyUtils.copyBeanList(
                tagService.getTagsByNoteId(vo.getId()), 
                TagVO.class
            ))
        );
        
        return Result.success(voPage);
    }
} 