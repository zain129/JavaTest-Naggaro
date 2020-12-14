/**
 * @author Zain Imtiaz
 **/

package com.zainimtiaz.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthSucessHandlerConfiguration implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		// 5m X 60sec = 300sec (+20sec time cushion)
		request.getSession(false).setMaxInactiveInterval(10);
		response.sendRedirect(request.getContextPath());
	}
}
