package com.ohyoung.exception;

import com.ohyoung.base.BaseException;
import com.ohyoung.base.ErrorCode;
import lombok.Getter;

import java.util.Map;

/**
 *  请求数据格式验证失败
 * @author vince
 */
@Getter
public class RequestValidationFailedException extends BaseException {

    public RequestValidationFailedException() {
        super(ErrorCode.REQUEST_VALIDATION_FAILED);
    }

    public RequestValidationFailedException(Map<String, Object> data) {
        super(ErrorCode.REQUEST_VALIDATION_FAILED, data);
    }
}
