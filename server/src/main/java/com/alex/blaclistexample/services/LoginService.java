package com.alex.blaclistexample.services;

import com.alex.blaclistexample.dto.LoginDto;
import com.alex.blaclistexample.dto.TokenDto;
import com.alex.blaclistexample.exceptions.LoginAndPasswordIncorrectException;
import com.alex.blaclistexample.exceptions.UserBannedException;
import com.alex.blaclistexample.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final PasswordEncoder passwordEncoder;

    private final StringRedisTemplate stringRedisTemplate;

    private final TokenService tokenService;

    private final UserService userService;

    @Autowired
    public LoginService(PasswordEncoder passwordEncoder,
                        StringRedisTemplate stringRedisTemplate,
                        TokenService tokenService,
                        UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.stringRedisTemplate = stringRedisTemplate;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    public TokenDto login(LoginDto loginDto, boolean isJwtEnabled) {
        User user = userService.findByUsername(loginDto.getUsername());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            throw new LoginAndPasswordIncorrectException();
        }

        boolean userBanned = Optional.ofNullable(stringRedisTemplate.hasKey(loginDto.getUsername()))
                .orElse(Boolean.FALSE);

        if (userBanned) throw new UserBannedException();

        return tokenService.getToken(user, isJwtEnabled);
    }
}
