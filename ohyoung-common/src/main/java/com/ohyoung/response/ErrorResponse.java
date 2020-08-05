package com.ohyoung.response;

import com.ohyoung.base.BaseException;
import com.ohyoung.base.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Map;

/**
 *  异常信息实体
 * @author ohYoung
 * @date 2020/8/2 13:36
 */
@Getter
@Setter
@ToString
public class ErrorResponse {

    /**
     *  自定义错误码
     */
    private Integer code;
    /**
     *  HTTP状态码
     */
    private Integer status;
    /**
     *  异常信息
     */
    private String message;
    /**
     *  请求路径
     */
    private String path;
    /**
     *  错误发生时的时间
     */
    private LocalDateTime time;
    /**
     *  对错误的补充信息
     */
    private Map<String, Object> data;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer status, String message, String path) {
        this.code = ErrorCode.REQUEST_VALIDATION_FAILED.getCode();
        this.status = status;
        this.message = message;
        this.path = path;
        this.time = LocalDateTime.now();
    }

    public ErrorResponse(BaseException e, String path) {
        this(e.getError().getCode(), e.getError().getStatus().value(), e.getError().getMessage(), path, e.getData());
    }

    public ErrorResponse(int code, int status, String message, String path, Map<String, Object> data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.time = LocalDateTime.now();
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

}
