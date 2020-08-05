package com.ohyoung.rest;

import com.ohyoung.exception.CustomAccessDeniedException;
import com.ohyoung.exception.NotLoggedInException;
import com.ohyoung.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  抛出异常的类
 * @author ohYoung
 * @date 2020/8/2 14:04
 */
@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class ExceptionController {

    /**
     * 未登录
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/notLoggedIn")
    @ResponseBody
    public ResponseEntity<String> notLoggedIn() {
        throw new NotLoggedInException();
    }

    /**
     * 未知请求(多指绕过网关发起的请求)
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/unknownRequest")
    @ResponseBody
    public ResponseEntity<String> unknownRequest() {
        throw new ResourceNotFoundException();
    }

    /**
     * 未登录
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/accessDenied")
    @ResponseBody
    public ResponseEntity<String> accessDenied() {
        throw new CustomAccessDeniedException();
    }

}
