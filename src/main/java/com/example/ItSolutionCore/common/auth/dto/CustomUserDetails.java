package com.example.ItSolutionCore.common.auth.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CustomUserDetails implements UserDetails {

    private final AuthMemberDto authMemberDto;

    public CustomUserDetails(AuthMemberDto authMemberDto) {

        this.authMemberDto = authMemberDto;
        log.info("AuthMemberDto received: {}", authMemberDto.toString());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authMemberDto.getRole().toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return authMemberDto.getPassword();
    }


    @Override
    public String getUsername() {
        return authMemberDto.getUsername();
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
