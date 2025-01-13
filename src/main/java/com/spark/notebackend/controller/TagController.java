package com.spark.notebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.notebackend.common.api.Result;
import com.spark.notebackend.entity.Tag;
import com.spark.notebackend.model.dto.TagDTO;
import com.spark.notebackend.model.vo.TagVO;
import com.spark.notebackend.service.TagService;
import com.spark.notebackend.util.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author spark
 * @Create 2025-01-13 20:34
 * @Version 1.0
 * @Description 标签控制器
 */
@RestController
@RequestMapping("/tags")
@Api(tags = "标签管理接口")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    @GetMapping("/page")
    @ApiOperation("分页查询标签")
    public Result<Page<TagVO>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") long current,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") long size,
            @ApiParam("用户ID") @RequestParam(required = false) Long userId,
            @ApiParam("标签名") @RequestParam(required = false) String tagName) {
        Page<Tag> page = new Page<>(current, size);
        Page<Tag> tagPage = tagService.pageTags(page, userId, tagName);
        
        // 使用工具类转换
        Page<TagVO> voPage = BeanCopyUtils.copyBeanPage(tagPage, TagVO.class);
        
        return Result.success(voPage);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询标签")
    public Result<TagVO> getById(@ApiParam("标签ID") @PathVariable Long id) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            return Result.fail("标签不存在");
        }
        TagVO vo = BeanCopyUtils.copyBean(tag, TagVO.class);
        return Result.success(vo);
    }

    @PostMapping
    @ApiOperation("创建标签")
    public Result<Boolean> save(@RequestBody @Validated TagDTO tagDTO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);
        return Result.success(tagService.save(tag));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新标签")
    public Result<Boolean> update(
            @ApiParam("标签ID") @PathVariable Long id,
            @RequestBody @Validated TagDTO tagDTO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);
        tag.setId(id);
        return Result.success(tagService.updateById(tag));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除标签")
    public Result<Boolean> remove(@ApiParam("标签ID") @PathVariable Long id) {
        return Result.success(tagService.removeById(id));
    }

    /**
     * 获取指定笔记关联的所有标签
     * 
     * @param noteId 笔记ID
     * @return 标签列表
     */
    @GetMapping("/note/{noteId}")
    @ApiOperation("获取笔记关联的标签")
    public Result<List<TagVO>> getTagsByNoteId(@ApiParam("笔记ID") @PathVariable Long noteId) {
        // 查询笔记关联的标签列表
        List<Tag> tags = tagService.getTagsByNoteId(noteId);
        // 转换为VO对象
        List<TagVO> vos = tags.stream()
                .map(tag -> {
                    TagVO vo = new TagVO();
                    BeanUtils.copyProperties(tag, vo);
                    return vo;
                }).collect(Collectors.toList());
        return Result.success(vos);
    }

    /**
     * 设置笔记的标签关联关系
     * 该操作会清除笔记原有的标签关联，然后建立新的关联关系
     *
     * @param noteId 笔记ID
     * @param tagIds 要关联的标签ID列表
     * @return 操作结果
     */
    @PostMapping("/note/{noteId}")
    @ApiOperation("设置笔记的标签")
    public Result<Void> setNoteTags(
            @ApiParam("笔记ID") @PathVariable Long noteId,
            @ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {
        // 更新笔记-标签关联关系
        tagService.setNoteTags(noteId, tagIds);
        return Result.success();
    }
} 