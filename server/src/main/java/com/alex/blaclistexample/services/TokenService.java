package com.alex.blaclistexample.services;

import com.alex.blaclistexample.dto.TokenDto;
import com.alex.blaclistexample.models.Token;
import com.alex.blaclistexample.models.User;
import com.alex.blaclistexample.repositories.TokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TokenService {

    private final String jwtSecret = "qwerty007";

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public User findUserByToken(String token) {
        Token tokenModel =  tokenRepository.findByToken(token)
                .orElseThrow(IllegalArgumentException::new);
        return tokenModel.getUser();
    }

    public TokenDto getToken(User user) {
        return getToken(user, false);
    }

    public TokenDto getToken(User user, boolean isJwtEnabled) {
        if (isJwtEnabled) {
            String token = RandomStringUtils.random(15, true, true);
            Token tokenModel = Token.builder()
                    .user(user)
                    .expiredDate(LocalDate.now().plusDays(2))
                    .token(token)
                    .build();
            tokenRepository.save(tokenModel);
            return TokenDto.builder()
                    .value(tokenModel.getToken())
                    .build();
        } else {
            String jwtToken = Jwts.builder()
                    .claim("role", user.getRole().toString())
                    .claim("username", user.getUsername())
                    .setSubject(user.getId().toString())
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
            return TokenDto.builder()
                    .value(jwtToken)
                    .build();
        }
    }
}
