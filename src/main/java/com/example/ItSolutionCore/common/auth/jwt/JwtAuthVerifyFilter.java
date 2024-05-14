package com.example.ItSolutionCore.common.auth.jwt;


import com.example.ItSolutionCore.common.auth.dto.AuthMemberDto;
import com.example.ItSolutionCore.common.auth.dto.CustomOAuth2User;
import com.example.ItSolutionCore.common.auth.dto.CustomUserDetails;
import com.example.ItSolutionCore.enums.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/*
*
* This class goes before JWTOAuth2VerfiyFilter. So, all cases should pass the request anyway.
* But, the case pass the filter(authentication success) should set the flag passedAuth = true to let next filter(JWTOAuth2VerifyFilter
* pass the chain)
*
* */

@Slf4j
@RequiredArgsConstructor
public class JwtAuthVerifyFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization= request.getHeader("Authorization");

        //Authorization header exist?
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.info("token null. header based auth verification failed.");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("authorization success!");
        //delete Bearer part to get pure token
        String token = authorization.split(" ")[1];


        //Expiration ver
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        /*
        * Token exist and valid. extract payload of JWT and authenticate user by spring security
        * Session doesn't store authentication because we set stateless for session handling.
        *
        * */
        //getting username & role from payload
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        AuthMemberDto authMemberDto = AuthMemberDto.builder()
                .username(username)
                .role(Role.valueOf(role))
                .build();

        // Spring security understandable OAuth2User Object
        CustomUserDetails customUserDetail = new CustomUserDetails(authMemberDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetail, null, customUserDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
//        request.setAttribute("passedAuth", true);
//        log.info("passedAuth set to true. will skip the JwtOAuth2VerifyFilter");
        filterChain.doFilter(request, response);
    }
}
