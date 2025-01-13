package com.spark.notebackend.common.exception;

import com.spark.notebackend.common.api.ErrorCode;
import com.spark.notebackend.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

/**
 * @Author spark
 * @Create 2025-01-13 19:03
 * @Version 1.0
 * @Description 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 1) 未知的系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("[System Exception]: {}", e.getMessage(), e);
        return Result.fail(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 2) 自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.warn("[Business Exception]: code={}, message={}", e.getCode(), e.getMessage(), e);
        // 优先使用业务异常里的 code，如无则默认为 BUSINESS_ERROR
        int code = e.getCode() != null ? e.getCode() : ErrorCode.BUSINESS_ERROR.getCode();
        return Result.fail(code, e.getMessage());
    }

    /**
     * 3) 参数校验异常 - 来自 Spring Validation
     *    当使用 @Valid/@Validated 校验 DTO 时，如果校验不通过会抛出此异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 取第一条校验失败的信息
        String errorMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("[Validation Exception]: {}", errorMsg, e);
        return Result.fail(ErrorCode.VALIDATION_ERROR, errorMsg);
    }


    /**
     * 4) 另一种参数校验异常 - ConstraintViolationException
     *    当使用 @Validated 在 Controller 方法参数上(非对象)时，校验失败会抛此异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMsg = e.getMessage(); // 或自行拼装
        log.warn("[Constraint Violation]: {}", errorMsg, e);
        return Result.fail(ErrorCode.VALIDATION_ERROR, errorMsg);
    }

    /**
     * 5) 权限不足异常 - 来自 Spring Security
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("[Access Denied]: {}", e.getMessage(), e);
        return Result.fail(ErrorCode.FORBIDDEN);
    }

    /**
     * 6) 针对未登录或认证失败的异常 (示例)
     *    例如:  AuthenticationException (Spring Security), JWT token invalid, etc.
     */
//    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
//    public Result<?> handleAuthenticationException(org.springframework.security.core.AuthenticationException e) {
//        log.warn("[Authentication Exception]: {}", e.getMessage(), e);
//        return Result.fail(ErrorCode.UNAUTHORIZED, "用户未登录或登录已失效");
//    }

    // 如果有更多类型异常，可继续添加


}
 