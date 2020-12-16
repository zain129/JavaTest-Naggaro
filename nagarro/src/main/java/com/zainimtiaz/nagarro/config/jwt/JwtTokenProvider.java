/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.config.jwt;

import com.zainimtiaz.nagarro.exception.InvalidJwtAuthException;
import com.zainimtiaz.nagarro.service.CustomUserDetailsService;
import com.zainimtiaz.nagarro.util.JwtProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class JwtTokenProvider {
//    @Value("${security.jwt.token.expire-length:300000}")
//    private long validityInMilliseconds = 300000; // 300,000ms = 5min

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    public Map createToken(String username, List<String> roles) {
        Map<String, Object> tokenData = new HashMap<>();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date(), validity = new Date(System.currentTimeMillis() + jwtProperties.getValidityInMs());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        tokenData.put("claims", claims);
        tokenData.put("issued", now);
        tokenData.put("validity", validity);
        tokenData.put("token", token);

        return tokenData;
    }

    public Authentication getAuthentication(String token) throws JwtException {
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

    public boolean validateToken(String token) throws ExpiredJwtException, InvalidJwtAuthException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        }
        return true;
    }

    public String invalidateToken(String token) {
        Date now = new Date();
        log.info("Time: " + now.toString());

        Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(now).setNotBefore(now).setExpiration(now);

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        return claimsJws.getBody();
    }
}
