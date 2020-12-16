/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

import io.jsonwebtoken.JwtException;

public class InvalidJwtAuthException extends JwtException {
    private static final long serialVersionUID = -1L;

    public InvalidJwtAuthException(String e) {
        super(e);
    }
}
