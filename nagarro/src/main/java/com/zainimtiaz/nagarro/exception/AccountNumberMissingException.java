/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.exception;

public class AccountNumberMissingException extends Exception {

    public AccountNumberMissingException() {

    }

    public AccountNumberMissingException(String message) {
        super(message);
    }
}
