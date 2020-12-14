/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthException extends AuthenticationException {
    private static final long serialVersionUID = -1L;

    public InvalidJwtAuthException(String e) {
        super(e);
    }
}
