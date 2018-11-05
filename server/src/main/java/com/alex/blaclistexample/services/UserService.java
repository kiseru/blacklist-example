package com.alex.blaclistexample.services;

import com.alex.blaclistexample.dto.RegistrationDto;
import com.alex.blaclistexample.dto.TokenDto;
import com.alex.blaclistexample.dto.UserDto;
import com.alex.blaclistexample.exceptions.LoginAndPasswordIncorrectException;
import com.alex.blaclistexample.models.Token;
import com.alex.blaclistexample.models.User;
import com.alex.blaclistexample.repositories.TokenRepository;
import com.alex.blaclistexample.repositories.UserRepository;
import com.alex.blaclistexample.security.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder,
                       TokenService tokenService,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    public TokenDto save(RegistrationDto registrationDto) {
        User user = User.builder()
                .username(registrationDto.getUsername())
                .passwordHash(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .build();
        return save(user);
    }

    public TokenDto save(User user, boolean isJwtEnabled) {
        userRepository.save(user);
        return tokenService.getToken(user, isJwtEnabled);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(LoginAndPasswordIncorrectException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public TokenDto save(User user) {
        return save(user, false);
    }

    public User findCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getDetails();
        return findByUsername(userDetails.getUsername());
    }
}
