/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.config;

import com.zainimtiaz.nagarro.entity.model.ActiveUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("customAuthenticationSuccessHandler")
public class CustomUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    ActiveUserList activeUserList;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            LoggedInUser user = new LoggedInUser(authentication.getName(), activeUserList);
            session.setAttribute("user", user);
        }
    }
}