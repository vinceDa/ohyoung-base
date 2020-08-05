package com.ohyoung.exception.handler;

import com.ohyoung.base.BaseException;
import com.ohyoung.exception.NotLoggedInException;
import com.ohyoung.response.ErrorResponse;
import com.ohyoung.rest.ExceptionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 *  统一异常处理
 * @author vince
 */
@Slf4j
@RestControllerAdvice(assignableTypes = {ExceptionController.class})
@ResponseBody
public class GlobalExceptionHandler {

    private static final int NOT_LOGGED_IN_CODE = 1005;

    /**
     *  处理未知异常
     * @param e  未知异常信息
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Throwable e, HttpServletRequest request) {
        e.printStackTrace();
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), e.toString(), request.getRequestURI());
        return buildErrorResponse(errorResponse);
    }

    /**
     *  处理自定义异常
     * @param e  自定义异常信息
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleCustomException(BaseException e, HttpServletRequest request) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e, request.getRequestURI());
        return buildErrorResponse(errorResponse);
    }

    /**
     *  处理参数校验异常
     * @param e  参数校验异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError single : fieldErrors) {
            errorMessages.add(single.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), errorMessages.toString(), request.getRequestURI());
        return buildErrorResponse(errorResponse);
    }

    /**
     * 统一返回
     * @param errorResponse 异常实体类
     * @return
     */
    private ResponseEntity<Object> buildErrorResponse(ErrorResponse errorResponse) {
        if (NOT_LOGGED_IN_CODE == errorResponse.getCode()) {
            return ResponseEntity.status(530).body(errorResponse);
        }
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
