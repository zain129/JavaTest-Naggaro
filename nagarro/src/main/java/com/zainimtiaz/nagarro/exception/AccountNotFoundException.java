/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() {

    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
