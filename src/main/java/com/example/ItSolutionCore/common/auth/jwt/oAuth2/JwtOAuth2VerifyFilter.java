package com.example.ItSolutionCore.common.auth.jwt.oAuth2;

import com.example.ItSolutionCore.common.auth.dto.CustomOAuth2User;
import com.example.ItSolutionCore.common.auth.dto.AuthMemberDto;
import com.example.ItSolutionCore.common.auth.jwt.JwtUtil;
import com.example.ItSolutionCore.enums.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
* OAuth2 authenticated users store JWT token into cookie. This class contains cookie based authentication verification logic.
* Check JwtAuth filter contains request header based authentication verification logic.
*
* This Filter goes after JwtAuthVerifyFilter. So, it should deny the request doesn't have Authorization cookie
* */

@RequiredArgsConstructor
@Slf4j
public class JwtOAuth2VerifyFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*
        * Checking previous filter's flag for pre-auth-success by header based verification.
        * */
        if(request.getAttribute("passedAuth") != null && (boolean) request.getAttribute("passedAuth")){
            log.info("it passed the auth verification from former filter JWTAuthVerificationFilter. No need for Cookie filter");
            filterChain.doFilter(request,response);
            return;
        }else log.info("it failed the auth verification from former filter JWTAuthVerifyFilter. Need cookie filter for Authentication..");




        String authorization = null;
        Cookie[] cookies= request.getCookies();
        if(cookies !=null){
        // add jwt token to authorization variable
            for(Cookie cookie: cookies){

                log.info("cookie name::"+ cookie.getName());
                if(cookie.getName().equals("Authorization")){
                    authorization = cookie.getValue();
                }
            }

        }

        log.info("authorization cookie = "+ authorization);

        //Token existence validation
        if(authorization == null){
            log.info("Cookie doesn't contain any authorization key. just pass to the next chain");
            filterChain.doFilter(request,response);
            return;
        }


        String token = authorization;

        //token expiry validation
        if(jwtUtil.isExpired(token)){
            log.info("Token expired");
            filterChain.doFilter(request,response);
            return;
        }

        //if token is valid
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        AuthMemberDto authMemberDto = AuthMemberDto.builder()
                .username(username)
                .role(Role.valueOf(role))
                .build();

        // Spring security understandable OAuth2User Object
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(authMemberDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);


    }
}
