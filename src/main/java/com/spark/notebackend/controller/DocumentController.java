package com.spark.notebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.notebackend.common.api.Result;
import com.spark.notebackend.entity.Document;
import com.spark.notebackend.model.dto.DocumentDTO;
import com.spark.notebackend.model.dto.DocumentQueryDTO;
import com.spark.notebackend.model.vo.DocumentVO;
import com.spark.notebackend.model.vo.TagVO;
import com.spark.notebackend.service.DocumentService;
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

/**
 * @Author spark
 * @Create 2025-01-13 23:22
 * @Version 1.0
 * @Description 文档控制器
 */
@RestController
@RequestMapping("/documents")
@Api(tags = "文档管理接口")
@RequiredArgsConstructor
@Validated
public class DocumentController {

    private final DocumentService documentService;
    private final TagService tagService;

    /**
     * 高级查询文档
     */
    @PostMapping("/query")
    @ApiOperation("高级查询文档")
    public Result<Page<DocumentVO>> advancedQuery(
            @ApiParam("页码") @RequestParam(defaultValue = "1") long current,
            @ApiParam("每页数量") @RequestParam(defaultValue = "10") long size,
            @RequestBody DocumentQueryDTO queryDTO) {
        Page<Document> page = new Page<>(current, size);
        Page<Document> docPage = documentService.advancedQuery(page, queryDTO);
        
        // 转换为VO对象
        Page<DocumentVO> voPage = BeanCopyUtils.copyBeanPage(docPage, DocumentVO.class);
        // 为每个文档加载关联的标签信息
        voPage.getRecords().forEach(vo -> 
            vo.setTags(BeanCopyUtils.copyBeanList(
                tagService.getTagsByDocumentId(vo.getId()), 
                TagVO.class
            ))
        );
        
        return Result.success(voPage);
    }

    /**
     * 查询用户可访问的文档列表
     */
    @GetMapping("/accessible")
    @ApiOperation("查询可访问的文档")
    public Result<List<DocumentVO>> getAccessibleDocuments(
            @ApiParam("用户ID") @RequestParam Long userId) {
        List<Document> docs = documentService.getAccessibleDocuments(userId);
        List<DocumentVO> vos = BeanCopyUtils.copyBeanList(docs, DocumentVO.class);
        // 为每个文档加载关联的标签信息
        vos.forEach(vo -> 
            vo.setTags(BeanCopyUtils.copyBeanList(
                tagService.getTagsByDocumentId(vo.getId()), 
                TagVO.class
            ))
        );
        return Result.success(vos);
    }

    /**
     * 创建文档
     */
    @PostMapping
    @ApiOperation("创建文档")
    public Result<Boolean> save(@RequestBody @Validated DocumentDTO documentDTO) {
        Document document = new Document();
        BeanUtils.copyProperties(documentDTO, document);
        return Result.success(documentService.save(document));
    }

    /**
     * 更新文档
     */
    @PutMapping("/{id}")
    @ApiOperation("更新文档")
    public Result<Boolean> update(
            @ApiParam("文档ID") @PathVariable Long id,
            @RequestBody @Validated DocumentDTO documentDTO) {
        Document document = new Document();
        BeanUtils.copyProperties(documentDTO, document);
        document.setId(id);
        return Result.success(documentService.updateById(document));
    }

    /**
     * 删除文档
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除文档")
    public Result<Boolean> remove(@ApiParam("文档ID") @PathVariable Long id) {
        return Result.success(documentService.removeById(id));
    }

    /**
     * 更新文档标签
     */
    @PostMapping("/{id}/tags")
    @ApiOperation("设置文档标签")
    public Result<Void> setDocumentTags(
            @ApiParam("文档ID") @PathVariable Long id,
            @ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {
        documentService.setDocumentTags(id, tagIds);
        return Result.success();
    }

    /**
     * 更新文档访问级别
     */
    @PutMapping("/{id}/access-level")
    @ApiOperation("更新文档访问级别")
    public Result<Boolean> updateAccessLevel(
            @ApiParam("文档ID") @PathVariable Long id,
            @ApiParam("访问级别") @RequestParam Integer accessLevel) {
        return Result.success(documentService.updateAccessLevel(id, accessLevel));
    }

    /**
     * 更新文档版本
     */
    @PutMapping("/{id}/version")
    @ApiOperation("更新文档版本")
    public Result<Boolean> updateVersion(
            @ApiParam("文档ID") @PathVariable Long id,
            @ApiParam("文档URL") @RequestParam String docUrl,
            @ApiParam("文档大小") @RequestParam Long docSize) {
        return Result.success(documentService.updateVersion(id, docUrl, docSize));
    }
} 