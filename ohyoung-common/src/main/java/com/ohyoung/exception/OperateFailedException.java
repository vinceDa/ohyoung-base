package com.ohyoung.exception;

import com.ohyoung.base.BaseException;
import com.ohyoung.base.ErrorCode;
import lombok.Getter;

import java.util.Map;

/**
 *  异常处理
 * @author vince
 */
@Getter
public class OperateFailedException extends BaseException {

    public OperateFailedException() {
        super(ErrorCode.OPERATE_FAILED);
    }

    public OperateFailedException(Map<String, Object> data) {
        super(ErrorCode.OPERATE_FAILED, data);
    }
}
