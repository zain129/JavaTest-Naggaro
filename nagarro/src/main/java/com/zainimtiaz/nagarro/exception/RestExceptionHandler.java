/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

import com.zainimtiaz.nagarro.entity.model.ActiveUserList;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;

@RestControllerAdvice(basePackages = {"com.zainimtiaz.nagarro"})
@Slf4j
public class RestExceptionHandler {
    @Autowired
    private ActiveUserList activeUserList;

    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity accountNotFound(AccountNotFoundException ex, WebRequest request) {
        log.debug("handling AccountNotFoundException...\n" + ex.getLocalizedMessage());
        Map<Object, Object> model = new HashMap<>();
        model.put("status", NOT_FOUND.value());
        model.put("error", NOT_FOUND.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity jwtException(JwtException ex, WebRequest request) {
        log.debug("handling jwtException...\n" + ex.getLocalizedMessage());

        List<String> users = activeUserList.getUsers();
        if (users.size() == 1) users = new ArrayList<>();
        else if (request.getUserPrincipal() != null) users.remove(request.getUserPrincipal().getName());
        activeUserList.setUsers(users);

        Map<Object, Object> model = new HashMap<>();
        model.put("status", BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {InvalidJwtAuthException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthException...\n" + ex.getLocalizedMessage());

        List<String> users = activeUserList.getUsers();
        if (users.size() == 1) users = new ArrayList<>();
        else if (request.getUserPrincipal() != null) users.remove(request.getUserPrincipal().getName());
        activeUserList.setUsers(users);

        Map<Object, Object> model = new HashMap<>();
        model.put("status", UNAUTHORIZED.value());
        model.put("error", UNAUTHORIZED.getReasonPhrase());
        model.put("message", "You are not allowed to access this API.");
        return ok(model);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity expiredJwtException(ExpiredJwtException ex, WebRequest request) {
        log.debug("handling ExpiredJwtException...\n" + ex.getLocalizedMessage());

        List<String> users = activeUserList.getUsers();
        if (users.size() == 1) users = new ArrayList<>();
        else if (request.getUserPrincipal() != null) users.remove(request.getUserPrincipal().getName());
        activeUserList.setUsers(users);

        Map<Object, Object> model = new HashMap<>();
        model.put("status", UNAUTHORIZED.value());
        model.put("error", UNAUTHORIZED.getReasonPhrase());
        model.put("message", "The provided JWT is Expired.");
        return ok(model);
    }

    @ExceptionHandler(value = {AccountNumberMissingException.class})
    public ResponseEntity accountNumberMissing(AccountNumberMissingException ex, WebRequest request) {
        log.debug("handling accountNumberMissing...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {InvalidDateRangeException.class})
    public ResponseEntity invalidDateRange(InvalidDateRangeException ex, WebRequest request) {
        log.debug("handling invalidDateRange...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {InvalidAmountRangeException.class})
    public ResponseEntity invalidAmountRange(InvalidAmountRangeException ex, WebRequest request) {
        log.debug("handling invalidAmountRange...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {ParseException.class})
    public ResponseEntity parseException(ParseException ex, WebRequest request) {
        log.debug("handling ParseException...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {ServletException.class})
    public ResponseEntity servletException(ServletException ex, WebRequest request) {
        log.debug("handling ServletException...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", UNPROCESSABLE_ENTITY.value());
        model.put("error", UNPROCESSABLE_ENTITY.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity ioException(IOException ex, WebRequest request) {
        log.debug("handling IOException...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", HttpStatus.BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity generalException(Exception ex, WebRequest request) {
        log.debug("handling all other Exceptions...\n " + ex.getMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity processRuntimeException(RuntimeException ex) {
        log.debug("handling RuntimeException...\n " + ex.getMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("status", BAD_REQUEST.value());
        model.put("error", BAD_REQUEST.getReasonPhrase());
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }
}
