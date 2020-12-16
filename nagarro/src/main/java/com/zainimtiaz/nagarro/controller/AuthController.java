/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.config.jwt.JwtTokenProvider;
import com.zainimtiaz.nagarro.entity.User;
import com.zainimtiaz.nagarro.entity.model.ActiveUserList;
import com.zainimtiaz.nagarro.entity.model.AuthRequest;
import com.zainimtiaz.nagarro.repository.UserMockRepository;
import com.zainimtiaz.nagarro.util.GeneralUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest authenticationRequest) {
        List<String> activeUsers = null, thisUserLoggedIn = null;
        Map<Object, Object> model = null;
        String username = "", token = "", tokenIssued = "", tokenValidity = "";

        try {
            activeUsers = activeUserList.getUsers();
            thisUserLoggedIn = new ArrayList<>();
            model = new HashMap<>();
            username = authenticationRequest.getUsername();

            if (activeUsers != null && !GeneralUtil.isNullorEmpty(activeUsers)) {
                String finalUsername = username;
                thisUserLoggedIn = activeUsers
                        .stream()
                        .filter(userName -> userName.equals(finalUsername))
                        .collect(toList());

                if (!GeneralUtil.isNullorEmpty(thisUserLoggedIn)) {
                    model.put("status", HttpStatus.BAD_REQUEST.value());
                    model.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
                    model.put("message", "User already loggedIn");
                    model.put("username", username);
                    return ok(model);
                }
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword()));
            User user = this.users.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Username " + username + "not found");
            } else {
                Map tokenData = jwtTokenProvider.createToken(username, user.getRoles());

                token = (String) tokenData.get("token");
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                tokenIssued = format.format((Date) tokenData.get("issued"));
                tokenValidity = format.format((Date) tokenData.get("validity"));
            }

            thisUserLoggedIn.add(username);
            activeUserList.setUsers(thisUserLoggedIn);

            model.put("status", HttpStatus.OK.value());
            model.put("message", "Logged-in successfully.");
            model.put("username", username);
            model.put("token", token);
            model.put("tokenIssued", tokenIssued);
            model.put("tokenValidUpto", tokenValidity);

            return ok(model);
        } catch (AuthenticationException e) {
            if (!GeneralUtil.isNullorEmpty(username)) thisUserLoggedIn.remove(username);
            if (activeUserList != null) activeUserList.setUsers(thisUserLoggedIn);
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout() throws Exception {
        List<String> activeUsers = null, thisUserLoggedIn = null;
        Map<Object, Object> model = null;
        String username = "", token = "";

        try {
            model = new HashMap<>();
            thisUserLoggedIn = new ArrayList<>();
            activeUsers = activeUserList.getUsers();

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            token = request.getHeader("Authorization").split(" ")[1];

            if (GeneralUtil.isNullorEmpty(token)) {
                throw new Exception("Token not found");
            }

            username = request.getUserPrincipal().getName();

            if (!GeneralUtil.isNullorEmpty(activeUsers)) {
                String finalUsername = username;
                thisUserLoggedIn = activeUsers.stream()
                        .filter(userName -> userName.equals(finalUsername))
                        .collect(toList());
            }
            if (GeneralUtil.isNullorEmpty(thisUserLoggedIn)) {
                model.put("status", HttpStatus.BAD_REQUEST.value());
                model.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
                model.put("message", "No such active user found.");
                model.put("username", username);
            } else {
                model.put("status", HttpStatus.OK.value());
                model.put("message", "Logged out successfully");
                model.put("username", username);
                model.put("token", jwtTokenProvider.invalidateToken(token));

                activeUsers.remove(username);
                activeUserList.setUsers(activeUsers);
            }
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @GetMapping("/expired")
    public ResponseEntity expired() throws Exception {
        Map<Object, Object> model = new HashMap<>();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];

        if (GeneralUtil.isNullorEmpty(token)) {
            throw new Exception("Token not found");
        }

        String username = request.getUserPrincipal().getName();
        List<String> activeUsers = activeUserList.getUsers(), thisUserLoggedIn = null;
        if (!GeneralUtil.isNullorEmpty(activeUsers)) {
            String finalUsername = username;
            thisUserLoggedIn = activeUsers.stream()
                    .filter(userName -> userName.equals(finalUsername))
                    .collect(toList());
        }
        if (!GeneralUtil.isNullorEmpty(thisUserLoggedIn)) {
            activeUsers.remove(username);
            activeUserList.setUsers(activeUsers);
        }

        model.put("status", HttpStatus.UNAUTHORIZED.value());
        model.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        model.put("message", "Session Expired!");
        return ok(model);
    }

    @GetMapping("/403")
    public ResponseEntity unauthorized() throws Exception {
        Map<Object, Object> model = new HashMap<>();
        model.put("status", HttpStatus.UNAUTHORIZED.value());
        model.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        model.put("message", "Access Denied - You are not authorized to perform this action.");
        return ok(model);
    }

    @GetMapping("/thisUser")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("status", HttpStatus.OK.value());
        model.put("message", "Details Found!");
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        return ok(model);
    }
}
