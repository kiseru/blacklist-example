package com.alex.blaclistexample.controller;

import com.alex.blaclistexample.dto.RegistrationDto;
import com.alex.blaclistexample.dto.TokenDto;
import com.alex.blaclistexample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign_up")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public TokenDto signUp(@RequestBody RegistrationDto registrationDto) {
        return userService.save(registrationDto);
    }
}
