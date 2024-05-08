package com.example.ItSolutionCore.common.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final AuthMemberDto authMemberDto;

    public CustomOAuth2User(AuthMemberDto authMemberDto) {

        this.authMemberDto = authMemberDto;
    }

    /*
    * I didn't use getAttributes method because the format of attributes really differ by the 3rd pt provider(google, facebook, okta, etc..)
    * */
    @Override
    public <A> A getAttribute(String name) {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return authMemberDto.getRole().toString();
            }
        });
        return collection;
    }

    @Override
    public String getName() {
        return authMemberDto.getName();
    }

    public String getUsername(){
        return authMemberDto.getUsername();
    }

    public String getBusiness() {
        return authMemberDto.getBusiness();
    }
}
