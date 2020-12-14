/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException authException) {
        log.debug("Jwt authentication failed:" + authException);
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt authentication failed");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }
}
