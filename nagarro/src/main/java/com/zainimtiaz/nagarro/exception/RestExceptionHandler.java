/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.ParseException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;
import static sun.security.timestamp.TSResponse.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity accountNotFound(AccountNotFoundException ex, WebRequest request) {
        log.debug("handling AccountNotFoundException...\n" + ex.getLocalizedMessage());
        return notFound().build();
    }

    @ExceptionHandler(value = {InvalidJwtAuthException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthException...\n" + ex.getLocalizedMessage());

        String response = "{\"error\" : \"" + UNAUTHORIZED + "\",\nmessage: \"You are not allowed to access this API.\" }";
        return status(UNAUTHORIZED).body(response).unprocessableEntity().build();
    }

    @ExceptionHandler(value = {AccountNumberMissingException.class})
    public ResponseEntity accountNumberMissing(AccountNumberMissingException ex, WebRequest request) {
        log.debug("handling accountNumberMissing...\n" + ex.getLocalizedMessage());

        String response = "{\"error\" : \"" + BAD_REQUEST + "\",\nmessage: \"" + ex.getLocalizedMessage() + "\" }";
        return status(BAD_REQUEST).body(response).badRequest().build();
    }

    @ExceptionHandler(value = {InvalidDateRangeException.class})
    public ResponseEntity invalidDateRange(InvalidDateRangeException ex, WebRequest request) {
        log.debug("handling invalidDateRange...\n" + ex.getLocalizedMessage());

        String response = "{\"error\" : \"" + BAD_REQUEST + "\",\nmessage: \"" + ex.getLocalizedMessage() + "\" }";
        return status(BAD_REQUEST).body(response).badRequest().build();
    }

    @ExceptionHandler(value = {InvalidAmountRangeException.class})
    public ResponseEntity invalidAmountRange(InvalidAmountRangeException ex, WebRequest request) {
        log.debug("handling invalidAmountRange...\n" + ex.getLocalizedMessage());

        String response = "{\"error\" : \"" + BAD_REQUEST + "\",\nmessage: \"" + ex.getLocalizedMessage() + "\" }";
        return status(BAD_REQUEST).body(response).badRequest().build();
    }

    @ExceptionHandler(value = {ParseException.class})
    public ResponseEntity parseException(ParseException ex, WebRequest request) {
        log.debug("handling ParseException...\n" + ex.getLocalizedMessage());

        String response = "{\"error\" : \"" + BAD_REQUEST + "\",\nmessage: \"" + ex.getLocalizedMessage() + "\" }";
        return status(BAD_REQUEST).body(response).badRequest().build();
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity generalException(Exception ex, WebRequest request) {
        log.debug("handling all other Exceptions...\n " + ex.getMessage());

        String response = "{\"error\" : \"" + BAD_REQUEST + "\",\nmessage: \"" + ex.getLocalizedMessage() + "\" }";
        return status(BAD_REQUEST).body(response).badRequest().build();
    }
}
