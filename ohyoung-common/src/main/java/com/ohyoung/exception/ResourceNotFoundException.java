package com.ohyoung.exception;

import com.ohyoung.base.BaseException;
import com.ohyoung.base.ErrorCode;
import lombok.Getter;

import java.util.Map;

/**
 *  未知请求异常处理
 * @author vince
 */
@Getter
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(Map<String, Object> data) {
        super(ErrorCode.RESOURCE_NOT_FOUND, data);
    }
}
