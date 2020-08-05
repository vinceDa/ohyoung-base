package com.ohyoung.exception;

import com.ohyoung.base.BaseException;
import com.ohyoung.base.ErrorCode;
import lombok.Getter;

import java.util.Map;

/**
 * 未登录异常处理
 *
 * @author vince
 */
@Getter
public class NotLoggedInException extends BaseException {

    public NotLoggedInException() {
        super(ErrorCode.NOT_LOGGED_IN);
    }

    public NotLoggedInException(Map<String, Object> data) {
        super(ErrorCode.NOT_LOGGED_IN, data);
    }
}
