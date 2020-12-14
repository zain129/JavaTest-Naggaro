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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Optional.ofNullable;
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
        try {
            String username = authenticationRequest.getUsername(), token = "";
            List<String> users = activeUserList.getUsers();

            log.info("User Req: " + username + "\nUsers: " + Arrays.toString(users.toArray()));

            if (users.contains(username)) {
                String response = "{\"username\" : \"" + username + "\",\nstatus: Logged-in,\nmessage: \"You are already logged-in\" }";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            users.add(username);
            activeUserList.setUsers(users);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword()));
            User user = this.users.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Username " + username + "not found");
            } else {
                token = jwtTokenProvider.createToken(username, (List<String>) user.getRoles());
            }
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String username = userDetails.getUsername();
            List<String> users = activeUserList.getUsers();
            log.info("User Req: " + username + "\nUsers: " + Arrays.toString(users.toArray()));

            if (!users.contains(username)) {
                String response = "{\"username\" : \"" + username + "\",\nstatus: Logged-out,\nmessage: \"You are already logged-out\" }";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            users.remove(username);
            activeUserList.setUsers(users);

//            jwtTokenProvider.invalidateToken("");

            return ResponseEntity.status(200).build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
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
