package com.alex.blaclistexample.controller;

import com.alex.blaclistexample.dto.LoginDto;
import com.alex.blaclistexample.dto.TokenDto;
import com.alex.blaclistexample.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public TokenDto login(@RequestParam(value = "jwt", required = false, defaultValue = "false") boolean isJwtEnabled,
                          @RequestBody LoginDto loginDto) {
        return loginService.login(loginDto, isJwtEnabled);
    }
}
