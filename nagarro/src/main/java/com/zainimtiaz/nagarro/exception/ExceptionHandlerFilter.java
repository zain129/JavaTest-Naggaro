/**
 * @author Zain I.
 * created on 16/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Autowired
    private RestExceptionHandler resolver;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException | IOException e) {
            log.error("Spring Security Filter Chain Exception:", e);
            if (e instanceof ExpiredJwtException) {
                ResponseEntity responseEntity = resolver.expiredJwtException((ExpiredJwtException) e, new ServletWebRequest(request));
                //set the response object
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "The JWT is expired");
                response.addHeader("status", "token expired");
            } else if (e instanceof InvalidJwtAuthException) {
                resolver.invalidJwtAuthentication((InvalidJwtAuthException) e, new ServletWebRequest(request));
            } else {
                resolver.processRuntimeException((RuntimeException) e);
            }
        }
    }
}
