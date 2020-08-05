package com.ohyoung.security.filter;

import cn.hutool.core.util.StrUtil;
import com.ohyoung.constant.AnonymousUriEnum;
import com.ohyoung.exception.RequestValidationFailedException;
import com.ohyoung.exception.NotLoggedInException;
import com.ohyoung.exception.ResourceNotFoundException;
import com.ohyoung.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  权限认证过滤器
 * @author ohYoung
 * @date 2020/7/30 17:25
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("jwtUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException {
        try {
            // todo 项目没有的接口(404)和不需要权限认证的接口直接放行, 不做无谓的查询数据库的操作
            String requestUri = request.getRequestURI();
            if (isAnonymousUri(requestUri)) {
                chain.doFilter(request, response);
                return;
            }
            // 验证token是否存在
            String token = request.getHeader(jwtTokenUtil.getHeader());
            if (!StrUtil.isEmpty(token)) {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        // 将用户信息存入 authentication，方便后续校验
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } else {
                throw new NotLoggedInException();
            }
            chain.doFilter(request, response);
        } catch (NotLoggedInException e) {
            log.error(e.getMessage());
            try {
                request.getRequestDispatcher("/api/v1/auth/notLoggedIn").forward(request, response);
            } catch (ServletException ex) {
                ex.printStackTrace();
            }
        }  catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            try {
                request.getRequestDispatcher("/api/v1/auth/unknownRequest").forward(request, response);
            } catch (ServletException ex) {
                ex.printStackTrace();
            }
        } catch (AccessDeniedException e) {
            log.error(e.getMessage());
            try {
                request.getRequestDispatcher("/api/v1/auth/accessDenied").forward(request, response);
            } catch (ServletException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAnonymousUri(String uri) {
        AnonymousUriEnum[] values = AnonymousUriEnum.values();
        for (AnonymousUriEnum value : values) {
            if (value.getValue().equals(uri)) {
                return true;
            }
        }
        return false;
    }
}
