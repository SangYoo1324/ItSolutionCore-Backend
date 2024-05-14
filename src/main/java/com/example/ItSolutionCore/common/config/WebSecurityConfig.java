package com.example.ItSolutionCore.common.config;


import com.example.ItSolutionCore.common.auth.jwt.*;
import com.example.ItSolutionCore.common.auth.jwt.oAuth2.CustomOAuth2SuccessHandler;
import com.example.ItSolutionCore.common.auth.jwt.oAuth2.JwtOAuth2VerifyFilter;
import com.example.ItSolutionCore.common.auth.service.CustomOauth2UserService;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final JwtUtil jwtUtil;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final MemberRepository memberRepository;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    public WebSecurityConfig(CustomOauth2UserService customOauth2UserService, CustomOAuth2SuccessHandler customOAuth2SuccessHandler,
                             JwtUtil jwtUtil, AuthenticationConfiguration authenticationConfiguration, AuthenticationManagerBuilder authenticationManagerBuilder, MemberRepository memberRepository, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.customOauth2UserService = customOauth2UserService;
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
        this.memberRepository = memberRepository;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }



    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .cors(corsCustomizer-> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(List.of("http://localhost:3000",
                                "https://www.sangbeomyooportfolio.com", "https://ps-its.com", "https://www.ps-its.com"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(List.of("GET, POST, PUT, DELETE, OPTIONS, PATCH, OPTIONS"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }))
                .csrf(csrf-> csrf.disable())
                .formLogin(auth->auth.disable())
                .httpBasic(auth->auth.disable())

                .oauth2Login((oauth2)->oauth2.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOauth2UserService)
                        )
                        .successHandler(customOAuth2SuccessHandler)
                )

                .exceptionHandling(exc-> exc.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(httpRequest->httpRequest
                        .requestMatchers("/api/signup/**", "/login", "/env","/hc").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/error").permitAll()// error path(not api url)
                        .requestMatchers("/api/auth/**").authenticated()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // for regular login filter
                .addFilterAt(new JwtLoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, memberRepository), UsernamePasswordAuthenticationFilter.class)
        //add JwtOAuth2VerifyFilter before  2nd parameter(UsernamePasswordAuthenticationFilter)
                .addFilterAfter(new JwtAuthVerifyFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtOAuth2VerifyFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // Need to customize the Exception handling to response back with 401 or 403

                ;
        return http.build();
    }

}
