package com.spark.notebackend.common.exception;

import com.spark.notebackend.common.api.ErrorCode;

/**
 * @Author spark
 * @Create 2025-01-13 19:06
 * @Version 1.0
 * @Description 业务异常
 */
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 自定义的错误码
     */
    private final Integer code;

    /**
     * 利用 ErrorCode 枚举构造业务异常
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 利用 ErrorCode 枚举 + 自定义错误消息构造业务异常
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    /**
     * 自定义传入 code & message
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 只有 message, code 默认为 BUSINESS_ERROR (1000) 或其他默认值
     */
    public BusinessException(String message) {
        super(message);
        this.code = 1000; // 可以使用一个默认的业务错误码
    }

    public Integer getCode() {
        return code;
    }
}
 