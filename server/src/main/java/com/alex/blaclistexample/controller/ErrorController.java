package com.alex.blaclistexample.controller;

import com.alex.blaclistexample.exceptions.LoginAndPasswordIncorrectException;
import com.alex.blaclistexample.exceptions.UserBannedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ErrorController {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUsernameNotFound() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginAndPasswordIncorrectException.class)
    public ResponseEntity handleLoginAndPasswordIncorrect(LoginAndPasswordIncorrectException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserBannedException.class)
    public ResponseEntity handleUserBanned(UserBannedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
