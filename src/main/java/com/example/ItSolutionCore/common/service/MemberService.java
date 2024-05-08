package com.example.ItSolutionCore.common.service;

import com.example.ItSolutionCore.common.auth.jwt.JwtUtil;
import com.example.ItSolutionCore.common.dto.MemberDto;
import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.repo.BusinessRepository;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import com.example.ItSolutionCore.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/*
* This Class is made for experimental use. After Authorization implementation, use AuthApiController or AuthService
* */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BusinessRepository businessRepository;
    private final JwtUtil jwtUtil;

    @Deprecated
    public MemberDto register(MemberDto memberDto) throws DataNotFoundException {

        Business target = businessRepository.findById(memberDto.getBusiness_id()).orElseThrow(()-> new DataNotFoundException("Business not Found.."));

       return  memberRepository.save(Member.builder()
                        .authority(Role.ROLE_USER)
//                        .password(memberDto.getPassword())
                        .provider("MANUAL")
                        .business(target)
                       .email(memberDto.getEmail())
                       .username(memberDto.getUsername())
                .build()).toDto();
    }
    @Deprecated
    public MemberDto login(MemberDto memberDto) throws DataNotFoundException{

        return memberRepository.findById(memberDto.getId()).orElseThrow(()-> new DataNotFoundException("member not found..")).toDto();
    }

    public MemberDto fetchUserInfo(String jwtToken) throws DataNotFoundException {
        String username = jwtUtil.getUsername(jwtToken);

        Member target = memberRepository.findByUsername(username).orElseThrow(()-> new DataNotFoundException("cannof find user"));

        return target.toDto();
    }
}
