package com.alex.blaclistexample.security;

import com.alex.blaclistexample.exceptions.UserBannedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenAuthProvider implements AuthenticationProvider {

    private String jwtSecret = "qwerty007";

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public JwtTokenAuthProvider(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtTokenAuth authToken = (JwtTokenAuth) authentication;
        Claims body;
        try {
            body = Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken.getName())
                    .getBody();
        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Invalid token");
        }

        UserDetails userDetails =
                new UserDetailsImpl(Long.parseLong(body.get("sub").toString()),
                        body.get("username").toString(),
                        body.get("role").toString());

        boolean userBanned = Optional.ofNullable(stringRedisTemplate.hasKey(userDetails.getUsername()))
                .orElse(Boolean.FALSE);

        System.out.println(userBanned);

        if (userBanned) throw new UserBannedException();

        authToken.setUserDetails(userDetails);
        authToken.setAuthenticated(true);
        return authToken;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return JwtTokenAuth.class.equals(auth);
    }
}
