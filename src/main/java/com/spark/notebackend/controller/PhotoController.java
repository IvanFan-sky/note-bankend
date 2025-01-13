package com.spark.notebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.notebackend.common.api.Result;
import com.spark.notebackend.entity.Photo;
import com.spark.notebackend.model.dto.PhotoDTO;
import com.spark.notebackend.model.dto.PhotoQueryDTO;
import com.spark.notebackend.model.vo.PhotoVO;
import com.spark.notebackend.service.PhotoService;
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
 * @Create 2025-01-14 00:41
 * @Version 1.0
 * @Description 照片管理控制器
 */
@RestController
@RequestMapping("/photos")
@Api(tags = "照片管理接口")
@RequiredArgsConstructor
@Validated
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    @ApiOperation("上传照片")
    public Result<Long> uploadPhoto(@RequestBody @Validated PhotoDTO photoDTO) {
        Photo photo = new Photo();
        BeanUtils.copyProperties(photoDTO, photo);
        photoService.save(photo);
        return Result.success(photo.getId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除照片")
    public Result<Void> deletePhoto(@ApiParam("照片ID") @PathVariable Long id) {
        photoService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}")
    @ApiOperation("更新照片信息")
    public Result<Void> updatePhoto(
            @ApiParam("照片ID") @PathVariable Long id,
            @RequestBody @Validated PhotoDTO photoDTO) {
        Photo photo = new Photo();
        BeanUtils.copyProperties(photoDTO, photo);
        photo.setId(id);
        photoService.updateById(photo);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取照片详情")
    public Result<PhotoVO> getPhoto(@ApiParam("照片ID") @PathVariable Long id) {
        Photo photo = photoService.getById(id);
        if (photo == null) {
            return Result.fail("照片不存在");
        }
        PhotoVO vo = new PhotoVO();
        BeanUtils.copyProperties(photo, vo);
        return Result.success(vo);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询照片")
    public Result<Page<PhotoVO>> pagePhotos(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Validated PhotoQueryDTO queryDTO) {
        Page<Photo> page = photoService.advancedQuery(new Page<>(current, size), queryDTO);
        Page<PhotoVO> voPage = new Page<>();
        BeanUtils.copyProperties(page, voPage, "records");
        List<PhotoVO> voList = page.getRecords().stream()
                .map(photo -> {
                    PhotoVO vo = new PhotoVO();
                    BeanUtils.copyProperties(photo, vo);
                    return vo;
                }).collect(Collectors.toList());
        voPage.setRecords(voList);
        return Result.success(voPage);
    }

    @GetMapping("/album/{albumId}")
    @ApiOperation("获取相册中的照片列表")
    public Result<List<PhotoVO>> getAlbumPhotos(
            @ApiParam("相册ID") @PathVariable Long albumId) {
        List<Photo> photos = photoService.getPhotosByAlbumId(albumId);
        List<PhotoVO> voList = photos.stream()
                .map(photo -> {
                    PhotoVO vo = new PhotoVO();
                    BeanUtils.copyProperties(photo, vo);
                    return vo;
                }).collect(Collectors.toList());
        return Result.success(voList);
    }

    @PutMapping("/{id}/tags")
    @ApiOperation("更新照片标签")
    public Result<Void> setPhotoTags(
            @ApiParam("照片ID") @PathVariable Long id,
            @RequestBody List<Long> tagIds) {
        photoService.setPhotoTags(id, tagIds);
        return Result.success();
    }

    @PutMapping("/{id}/sort-order")
    @ApiOperation("更新照片排序序号")
    public Result<Void> updateSortOrder(
            @ApiParam("照片ID") @PathVariable Long id,
            @ApiParam("排序序号") @RequestParam Integer sortOrder) {
        photoService.updateSortOrder(id, sortOrder);
        return Result.success();
    }

    @PutMapping("/batch-sort")
    @ApiOperation("批量更新照片排序")
    public Result<Void> batchUpdateSortOrder(
            @ApiParam("照片ID列表") @RequestBody List<Long> photoIds) {
        photoService.batchUpdateSortOrder(photoIds);
        return Result.success();
    }
} 