package com.example.ItSolutionCore.common.auth.jwt;

import com.example.ItSolutionCore.common.auth.dto.CustomUserDetails;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
/*
*
* This Filter works for http://localhost:8080/login api(form login url)
* because UsernamePasswordAuthenticationFilter works for form login url
* but, we disabled form login on the webSecurity filterChain, so we just implement manually
* */

@Slf4j
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final MemberRepository memberRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);

        String password=  obtainPassword(request).toString();

        log.info("Username, password from JwtLoginFilter");
        log.info(username+", " +password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);
        log.info("authenticationToken:: "+authenticationToken.toString());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

       log.info("Login Success by JwtLogin Filter");

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        log.info("Granted Authorities:" );
    ((CustomUserDetails) authResult.getPrincipal()).getAuthorities().stream().forEach(a->log.info(a.getAuthority().toString()));

        String useranme = customUserDetails.getUsername();
        Iterator<? extends GrantedAuthority> it = authResult.getAuthorities().iterator();
       String token = jwtUtil.createJwt(useranme, it.next().getAuthority(), false);

       log.info("Login attempt generated new Token:: {}", token);
        response.addHeader("Authorization", "Bearer "+token);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("Login Failed by JwtLogin Filter");
        response.setStatus(401);
    }
}
