package com.example.ItSolutionCore.common.auth.jwt.oAuth2;

import com.example.ItSolutionCore.common.auth.dto.CustomOAuth2User;
import com.example.ItSolutionCore.common.auth.jwt.JwtUtil;
import com.example.ItSolutionCore.common.util.OriginDeterminer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Value("${spring.jwt.exp}")
    private String exp;

    private final HttpSession httpSession;

    private final JwtUtil jwtUtil;

    private final OriginDeterminer originDeterminer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String redirectUrl = null;
        String redirectTo =(String) httpSession.getAttribute("business");
        if (redirectTo != null && !redirectTo.isEmpty()) {
            // 기본 리디렉션 URL
            log.info("redirctTo = {}", redirectTo);
            redirectUrl = originDeterminer.OAuth2OriginDeterminer(redirectTo);
            log.info("redirctUrl 이 세팅 되었습니다 = {}", redirectUrl);

        } else {
            // 클라이언트에서 전달된 리디렉션 URL을 사용
            log.info("redirctUrl이 비었습니다. itSolutioinCore 으로 이동합니다");

        }

//        String requestUrl = request.getRequestURI();
//        log.info("request URL:::" + requestUrl);


//        String business = (String) httpSession.getAttribute("business");
//        log.info("Session stored business name to redirect = "+ business);

        //OAuth2User
        CustomOAuth2User customOAuth2UserDetails = (CustomOAuth2User) authentication.getPrincipal();


        String username = customOAuth2UserDetails.getUsername();


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        log.info("SuccessHandler. getting authorities from principal. "+ auth);
        String role = auth.getAuthority();
        log.info("SuccessHandler. getting authorities from principal.getAuthority() "+ auth);
        String token = jwtUtil.createJwt(username, role);

        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect(redirectUrl);

    }

    private Cookie createCookie(String key, String val){

        Cookie cookie = new Cookie(key, val);
        cookie.setMaxAge((int) (Long.parseLong(exp)*60));
        cookie.setPath("/");
        cookie.setDomain("www.ps-its.com");
        cookie.setSecure(true);
        cookie.setHttpOnly(false);
        cookie.getAttributes().forEach((key1, value) -> log.info(key1 + "," + value));
        return cookie;
    }



}
