/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity accountNotFound(AccountNotFoundException ex, WebRequest request) {
        log.debug("handling AccountNotFoundException...\n" + ex.getLocalizedMessage());
        return notFound().build();
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity jwtException(JwtException ex, WebRequest request) {
        log.debug("handling jwtException...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("error", BAD_REQUEST);
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {InvalidJwtAuthException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthException...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("error", UNAUTHORIZED);
        model.put("message", "You are not allowed to access this API.");
        return ok(model);
    }

    @ExceptionHandler(value = {AccountNumberMissingException.class})
    public ResponseEntity accountNumberMissing(AccountNumberMissingException ex, WebRequest request) {
        log.debug("handling accountNumberMissing...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("error", BAD_REQUEST);
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {InvalidDateRangeException.class})
    public ResponseEntity invalidDateRange(InvalidDateRangeException ex, WebRequest request) {
        log.debug("handling invalidDateRange...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("error", BAD_REQUEST);
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {InvalidAmountRangeException.class})
    public ResponseEntity invalidAmountRange(InvalidAmountRangeException ex, WebRequest request) {
        log.debug("handling invalidAmountRange...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("error", BAD_REQUEST);
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {ParseException.class})
    public ResponseEntity parseException(ParseException ex, WebRequest request) {
        log.debug("handling ParseException...\n" + ex.getLocalizedMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("error", BAD_REQUEST);
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity generalException(Exception ex, WebRequest request) {
        log.debug("handling all other Exceptions...\n " + ex.getMessage());

        Map<Object, Object> model = new HashMap<>();
        model.put("error", BAD_REQUEST);
        model.put("message", ex.getLocalizedMessage());
        return ok(model);
    }
}
