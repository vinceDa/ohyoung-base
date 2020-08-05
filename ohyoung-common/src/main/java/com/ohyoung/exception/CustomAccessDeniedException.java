package com.ohyoung.exception;

import com.ohyoung.base.BaseException;
import com.ohyoung.base.ErrorCode;
import lombok.Getter;

import java.util.Map;

/**
 *  权限不足异常处理
 * @author vince
 */
@Getter
public class CustomAccessDeniedException extends BaseException {

    public CustomAccessDeniedException() {
        super(ErrorCode.REQUEST_UNAUTHORIZED);
    }

    public CustomAccessDeniedException(Map<String, Object> data) {
        super(ErrorCode.REQUEST_UNAUTHORIZED, data);
    }
}
