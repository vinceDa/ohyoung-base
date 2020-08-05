package com.ohyoung.security.rest;

import com.ohyoung.exception.NotLoggedInException;
import com.ohyoung.exception.ResourceNotFoundException;
import com.ohyoung.security.domain.AuthorizationUser;
import com.ohyoung.security.domain.JwtUser;
import com.ohyoung.security.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *  授权、根据token获取用户详细信息
 * @author vince
 * @date 2019-12-11
 */
@Slf4j
@Api(tags = "系统管理: 权限管理")
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * 登录授权
     * 
     * @param authorizationUser
     *            传入的用户信息
     * @return 成功返回token, 失败返回对应的错误信息
     */
    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthorizationUser authorizationUser) {
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(authorizationUser.getUsername());
        if (!jwtUser.getPassword().equals(authorizationUser.getPassword())) {
            throw new AccountExpiredException("密码错误");
        }
        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }
        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);
        log.info("token isExpired: {}", jwtTokenUtil.isTokenExpired(token));
        // 返回 token
        return ResponseEntity.ok(token);
    }

    /**
     * 获取用户信息
     * 
     * @return
     */
    @ApiOperation(value = "获取当前用户信息")
    @GetMapping(value = "/getCurrentUser")
    public ResponseEntity<Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(jwtUser);
    }

}
