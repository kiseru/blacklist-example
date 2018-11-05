package com.alex.blaclistexample.services;

import com.alex.blaclistexample.dto.TokenDto;
import com.alex.blaclistexample.models.User;
import com.alex.blaclistexample.repositories.UserRepository;
import com.alex.blaclistexample.security.Role;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminService {

    private final StringRedisTemplate stringRedisTemplate;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AdminService(StringRedisTemplate stringRedisTemplate,
                        TokenService tokenService,
                        UserRepository userRepository) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public TokenDto makeAdmin(String username) {
        return changeRole(username, Role.ADMIN);
    }

    public TokenDto makeUser(String username) {
        return changeRole(username, Role.USER);
    }

    private TokenDto changeRole(String username,
                                Role role) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(IllegalArgumentException::new);
        user.setRole(role);
        userRepository.save(user);

        return tokenService.getToken(user);
    }

    public void block(String username) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(username, "banned", 2, TimeUnit.DAYS);
    }
}
