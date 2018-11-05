package com.alex.blaclistexample.controller;

import com.alex.blaclistexample.dto.UserDto;
import com.alex.blaclistexample.models.User;
import com.alex.blaclistexample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> getAllUsers(@RequestHeader String authToken) {
        List<User> users = userService.findAll();
        return UserDto.from(users);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserDto getMySelf(@RequestHeader String authToken) {
        return UserDto.from(userService.findCurrentUser());
    }
}
