package com.ohyoung.base;

import org.springframework.http.HttpStatus;

/**
 * 错误码枚举
 * @author ohYoung
 * @date 2020/8/2 13:22
 */
public enum ErrorCode {

    /**
     *  状态码
     */
    RESOURCE_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "未找到该资源"),
    REQUEST_VALIDATION_FAILED(1002, HttpStatus.BAD_REQUEST, "请求数据格式验证失败"),
    REQUEST_LIMIT_EXCEEDED(1003, HttpStatus.BAD_REQUEST, "请求次数超限"),
    REQUEST_UNAUTHORIZED(1004, HttpStatus.UNAUTHORIZED, "请求未授权"),
    NOT_LOGGED_IN(1005, HttpStatus.BAD_REQUEST, "未登录"),
    OPERATE_FAILED(9999, HttpStatus.BAD_REQUEST, "操作异常");


    private final int code;

    private final HttpStatus status;

    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

}
