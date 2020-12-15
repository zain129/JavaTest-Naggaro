/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.config.jwt.JwtTokenProvider;
import com.zainimtiaz.nagarro.entity.User;
import com.zainimtiaz.nagarro.model.ActiveUserList;
import com.zainimtiaz.nagarro.model.AuthRequest;
import com.zainimtiaz.nagarro.repository.UserMockRepository;
import com.zainimtiaz.nagarro.util.GeneralUtil;
import com.zainimtiaz.nagarro.util.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMockRepository users;
    @Autowired
    private ActiveUserList activeUserList;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest authenticationRequest) {
        try {
            String username = authenticationRequest.getUsername(), token = "";
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword()));
            User user = this.users.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Username " + username + "not found");
            } else {
                token = jwtTokenProvider.createToken(username, user.getRoles());
            }
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout() throws Exception {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization").split(" ")[1];

            if (GeneralUtil.isNullorEmpty(token)) {
                throw new Exception("Token not found");
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("message", "Logged out successfully");
            model.put("token", jwtTokenProvider.invalidateToken(token));
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @GetMapping("/expired")
    public ResponseEntity expired() throws Exception {
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "Session Expired!");
        return ok(model);
    }

    @GetMapping("/thisUser")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        return ok(model);
    }
}
