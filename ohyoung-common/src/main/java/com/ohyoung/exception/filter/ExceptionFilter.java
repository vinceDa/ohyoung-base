/*
package com.ohyoung.exception.filter;

import com.ohyoung.exception.NotLoggedInException;
import com.ohyoung.exception.UnknownRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

*/
/**
 * @author vince
 * @date 2019/11/11 23:14
 *//*


@Slf4j
@Component
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (NotLoggedInException e) {
            log.error(e.getMessage());
            try {
                request.getRequestDispatcher("/api/v1/auth/notLoggedIn").forward(request, response);
            } catch (ServletException ex) {
                ex.printStackTrace();
            }
        }  catch (UnknownRequestException e) {
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
}
*/
