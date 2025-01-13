package com.spark.notebackend.common.api;

/**
 * @Author spark
 * @Create 2025-01-13 19:00
 * @Version 1.0
 * @Description 错误码枚举错误码枚举
 */
public enum ErrorCode {
    // 成功
    SUCCESS(200, "操作成功"),

    // 基础错误
    SYSTEM_ERROR(500, "系统繁忙，请稍后再试"),
    INVALID_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "用户未登录或登录已过期"),
    FORBIDDEN(403, "没有访问权限"),
    NOT_FOUND(404, "请求资源未找到"),

    // 自定义业务错误（可根据业务场景添加）
    BUSINESS_ERROR(1000, "业务异常"),
    USER_EXISTS(1001, "用户已存在"),
    USER_NOT_EXISTS(1002, "用户不存在"),
    PASSWORD_ERROR(1003, "密码错误"),
    USER_DISABLED(1004, "用户已被禁用"),
    USER_NOT_LOGIN(1005, "用户未登录"),
    USER_NOT_AUTHORIZED(1006, "用户没有权限"),
    USER_NOT_ENABLED(1007, "用户未启用"),
    DATA_CONFLICT(1002, "数据冲突"),
    // ... 其它业务错误码

    // 校验错误（参数不符合要求）
    VALIDATION_ERROR(2000, "参数校验失败");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}