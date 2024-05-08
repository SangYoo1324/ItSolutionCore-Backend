package com.example.ItSolutionCore.common.auth.service;


import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.common.auth.jwt.JwtUtil;
import com.example.ItSolutionCore.common.dto.MailDto;
import com.example.ItSolutionCore.common.dto.MemberDto;
import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.repo.BusinessRepository;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import com.example.ItSolutionCore.common.service.MailService;
import com.example.ItSolutionCore.common.util.OriginDeterminer;
import com.example.ItSolutionCore.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final BusinessRepository businessRepository;

    private final JwtUtil jwtUtil;

    private final MailService mailService;

    @Value("${server.publicAddress}")
    private String publicAddress;

    private final OriginDeterminer originDeterminer;
    public String Signup(MemberDto memberDto, String origin) throws DataNotFoundException {
        log.info("memberDto::: "+memberDto);

       Business business =  businessRepository.findById(Optional.ofNullable(memberDto.getBusiness_id()).orElse(1l)).orElseThrow(()->new DataNotFoundException("Cannot find business ID from Signup DTO."));
       String token =jwtUtil.createSignupToken(memberDto.getUsername(), memberDto.getEmail());
       mailService.sendDirectMail(MailDto.builder()
                       .subject("Please verify your email Address")
               .message("Hi! Please click below link to verify your email address!\n"
                       +publicAddress+"/api/signup/verification"+"?token="+token+"&origin="+originDeterminer.OAuth2OriginDeterminer(origin))
               .build(), List.of(memberDto.getEmail()));

        memberRepository.save(Member.builder()
                .provider(null)
                .email(memberDto.getEmail())
                .authority(Role.ROLE_USER)
                //username = name for regular signup method(Oauth2 has username & name separated)
                .name(memberDto.getUsername())
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .business(business)
                .build());

      return  token;



    }

    public void Signup_email_verify(String token) throws Exception{
        log.info("Signup_email_verify");
        log.info("token::"+ token);
        if(jwtUtil.isExpired(token)){
            throw new Exception("token expired");
        }else{
            Member member = memberRepository.findByUsernameAndEmail(jwtUtil.getUsername(token),jwtUtil.getEmail(token)).orElseThrow(()-> new Exception("resolved token info isn't valid"));
            member.setVerified(true);
            memberRepository.save(member);
        }

    }



}
