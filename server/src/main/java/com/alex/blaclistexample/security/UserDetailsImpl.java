package com.alex.blaclistexample.security;

import com.alex.blaclistexample.models.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    @Getter
    private User user;

    private GrantedAuthority grantedAuthority;

    public UserDetailsImpl(User user) {
        this.user = user;
        grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
    }

    public UserDetailsImpl(Long id,
                           String username,
                           String role) {
        this(User.builder()
                .id(id)
                .username(username)
                .role(Role.valueOf(role))
                .build());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
