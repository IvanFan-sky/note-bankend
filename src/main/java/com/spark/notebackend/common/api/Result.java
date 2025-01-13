package com.spark.notebackend.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author spark
 * @Create 2025-01-13 18:59
 * @Version 1.0
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;       // 状态码
    private String message;     // 消息描述
    private T data;            // 返回数据

    // =============== 成功 ===============

    public static <T> Result<T> success() {
        return new Result<>(ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getMessage(),
                null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getMessage(),
                data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ErrorCode.SUCCESS.getCode(), message, data);
    }

    // =============== 失败 ===============

    // 1) 直接使用已有的 ErrorCode 枚举
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    // 2) 使用枚举 + 自定义提示信息
    public static <T> Result<T> fail(ErrorCode errorCode, String customMsg) {
        return new Result<>(errorCode.getCode(), customMsg, null);
    }

    // 3) 使用自定义 code & message
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    // 4) 最简便，默认 BUSINESS_ERROR code
    public static <T> Result<T> fail(String message) {
        return new Result<>(ErrorCode.BUSINESS_ERROR.getCode(), message, null);
    }
}
 