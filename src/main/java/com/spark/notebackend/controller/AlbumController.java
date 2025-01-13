package com.spark.notebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.notebackend.common.api.Result;
import com.spark.notebackend.entity.Album;
import com.spark.notebackend.model.dto.AlbumDTO;
import com.spark.notebackend.model.dto.AlbumQueryDTO;
import com.spark.notebackend.model.vo.AlbumVO;
import com.spark.notebackend.service.AlbumService;
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
 * @Create 2025-01-14 00:40
 * @Version 1.0
 * @Description 相册管理控制器
 */
@RestController
@RequestMapping("/albums")
@Api(tags = "相册管理接口")
@RequiredArgsConstructor
@Validated
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    @ApiOperation("创建相册")
    public Result<Long> createAlbum(@RequestBody @Validated AlbumDTO albumDTO) {
        Album album = new Album();
        BeanUtils.copyProperties(albumDTO, album);
        albumService.save(album);
        return Result.success(album.getId());
    }

    @PutMapping("/{id}")
    @ApiOperation("更新相册")
    public Result<Void> updateAlbum(
            @ApiParam("相册ID") @PathVariable Long id,
            @RequestBody @Validated AlbumDTO albumDTO) {
        Album album = new Album();
        BeanUtils.copyProperties(albumDTO, album);
        album.setId(id);
        albumService.updateById(album);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除相册")
    public Result<Void> deleteAlbum(@ApiParam("相册ID") @PathVariable Long id) {
        albumService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取相册详情")
    public Result<AlbumVO> getAlbum(@ApiParam("相册ID") @PathVariable Long id) {
        Album album = albumService.getById(id);
        if (album == null) {
            return Result.fail("相册不存在");
        }
        AlbumVO vo = new AlbumVO();
        BeanUtils.copyProperties(album, vo);
        return Result.success(vo);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询相册")
    public Result<Page<AlbumVO>> pageAlbums(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Validated AlbumQueryDTO queryDTO) {
        Page<Album> page = albumService.advancedQuery(new Page<>(current, size), queryDTO);
        Page<AlbumVO> voPage = new Page<>();
        BeanUtils.copyProperties(page, voPage, "records");
        List<AlbumVO> voList = page.getRecords().stream()
                .map(album -> {
                    AlbumVO vo = new AlbumVO();
                    BeanUtils.copyProperties(album, vo);
                    return vo;
                }).collect(java.util.stream.Collectors.toList());
        voPage.setRecords(voList);
        return Result.success(voPage);
    }

    @GetMapping("/accessible/{userId}")
    @ApiOperation("获取用户可访问的相册列表")
    public Result<List<AlbumVO>> getAccessibleAlbums(
            @ApiParam("用户ID") @PathVariable Long userId) {
        List<Album> albums = albumService.getAccessibleAlbums(userId);
        List<AlbumVO> voList = albums.stream()
                .map(album -> {
                    AlbumVO vo = new AlbumVO();
                    BeanUtils.copyProperties(album, vo);
                    return vo;
                }).collect(java.util.stream.Collectors.toList());
        return Result.success(voList);
    }

    @PutMapping("/{id}/tags")
    @ApiOperation("更新相册标签")
    public Result<Void> setAlbumTags(
            @ApiParam("相册ID") @PathVariable Long id,
            @RequestBody List<Long> tagIds) {
        albumService.setAlbumTags(id, tagIds);
        return Result.success();
    }

    @PutMapping("/{id}/access-level")
    @ApiOperation("更新相册访问级别")
    public Result<Void> updateAccessLevel(
            @ApiParam("相册ID") @PathVariable Long id,
            @ApiParam("访问级别") @RequestParam Integer accessLevel) {
        albumService.updateAccessLevel(id, accessLevel);
        return Result.success();
    }
} 