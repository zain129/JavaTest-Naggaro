/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.config.jwt;

import com.zainimtiaz.nagarro.exception.InvalidJwtAuthException;
import com.zainimtiaz.nagarro.model.ActiveUserList;
import com.zainimtiaz.nagarro.service.CustomUserDetailsService;
import com.zainimtiaz.nagarro.util.JwtProperties;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
//    @Value("${security.jwt.token.expire-length:30000}")
//    private long validityInMilliseconds = 300000; // 30,000ms = 5min

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ActiveUserList activeUserList;

    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ?
                bearerToken.substring(7, bearerToken.length()) : null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !(claims.getBody().getExpiration().before(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            List<String> users = activeUserList.getUsers();
            UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
            userDetails;
            throw new InvalidJwtAuthException("Expired/Invalid JWT token");
        }
    }

    public String invalidateToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        claims.setExpiration(new Date());

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }
}
