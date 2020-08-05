package com.ohyoung.base;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  自定义异常基类
 * @author ohYoung
 * @date 2020/8/2 13:07
 */
public class BaseException extends RuntimeException {

    private final ErrorCode error;
    private final HashMap<String, Object> data = new HashMap<>();

    public BaseException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public BaseException(ErrorCode error, Map<String, Object> data) {
        super(error.getMessage());
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    protected BaseException(ErrorCode error, Map<String, Object> data, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public ErrorCode getError() {
        return error;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
