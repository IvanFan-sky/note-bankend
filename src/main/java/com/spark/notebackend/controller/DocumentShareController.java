//package com.spark.notebackend.controller;

//import com.spark.notebackend.common.api.Result;
//import com.spark.notebackend.model.dto.DocumentShareDTO;
//import com.spark.notebackend.model.vo.DocumentShareVO;
//import com.spark.notebackend.service.DocumentShareService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @Author spark
// * @Create 2025-01-13 23:51
// * @Version 1.0
// * @Description 文档共享控制器
// */
//@RestController
//@RequestMapping("/document-shares")
//@Api(tags = "文档共享管理接口")
//@RequiredArgsConstructor
//@Validated
//public class DocumentShareController {
//
//    private final DocumentShareService documentShareService;
//
////    @PostMapping
////    @ApiOperation("共享文档")
////    public Result<Void> shareDocument(@RequestBody @Validated DocumentShareDTO shareDTO) {
////        // 验证当前用户是否有权限共享文档
////        if (!documentShareService.hasEditPermission(shareDTO.getDocumentId(), getCurrentUserId())) {
////            return Result.fail("没有权限共享此文档");
////        }
////        documentShareService.shareDocument(shareDTO);
////        return Result.success();
////    }
//
//    @DeleteMapping("/{documentId}/{userId}")
//    @ApiOperation("取消共享")
//    public Result<Void> cancelShare(
//            @ApiParam("文档ID") @PathVariable Long documentId,
//            @ApiParam("用户ID") @PathVariable Long userId) {
//        // 验证当前用户是否有权限取消共享
//        if (!documentShareService.hasEditPermission(documentId, getCurrentUserId())) {
//            return Result.fail("没有权限取消共享");
//        }
//        documentShareService.cancelShare(documentId, userId);
//        return Result.success();
//    }
//
//    @GetMapping("/document/{documentId}")
//    @ApiOperation("获取文档的共享用户列表")
//    public Result<List<DocumentShareVO>> getDocumentShares(
//            @ApiParam("文档ID") @PathVariable Long documentId) {
//        // 验证当前用户是否有权限查看共享列表
//        if (!documentShareService.hasPermission(documentId, getCurrentUserId())) {
//            return Result.fail("没有权限查看此文档的共享列表");
//        }
//        return Result.success(documentShareService.getDocumentShares(documentId));
//    }
//
//    @GetMapping("/user/{userId}")
//    @ApiOperation("获取用户的共享文档列表")
//    public Result<List<DocumentShareVO>> getUserShares(
//            @ApiParam("用户ID") @PathVariable Long userId) {
//        return Result.success(documentShareService.getUserShares(userId));
//    }
//
//    @PutMapping("/{documentId}/{userId}/permission")
//    @ApiOperation("更新共享权限")
//    public Result<Boolean> updatePermission(
//            @ApiParam("文档ID") @PathVariable Long documentId,
//            @ApiParam("用户ID") @PathVariable Long userId,
//            @ApiParam("权限") @RequestParam Integer permission) {
//        // 验证当前用户是否有权限更新共享权限
//        if (!documentShareService.hasEditPermission(documentId, getCurrentUserId())) {
//            return Result.fail("没有权限更新共享权限");
//        }
//        return Result.success(documentShareService.updatePermission(documentId, userId, permission));
//    }
//
//    /**
//     * 获取当前登录用户ID
//     */
//    private Long getCurrentUserId() {
//        // TODO: 从实际的用户认证系统中获取
//        return 1L; // 临时返回固定值，需要替换为实际实现
//    }
//}