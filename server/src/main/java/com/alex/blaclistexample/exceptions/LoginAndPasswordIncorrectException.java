package com.alex.blaclistexample.exceptions;

public class LoginAndPasswordIncorrectException extends RuntimeException {

    public LoginAndPasswordIncorrectException() {
        super("User login/password incorrect");
    }
}
