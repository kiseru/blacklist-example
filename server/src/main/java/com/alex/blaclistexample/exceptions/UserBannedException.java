package com.alex.blaclistexample.exceptions;

public class UserBannedException extends RuntimeException {
    public UserBannedException() {
        super("User banned.");
    }
}
