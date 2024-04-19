package com.example.ItSolutionCore.common.service;

import com.example.ItSolutionCore.common.dto.MemberDto;
import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.common.entity.Member;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.repo.BusinessRepository;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import com.example.ItSolutionCore.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BusinessRepository businessRepository;

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

    public MemberDto login(MemberDto memberDto) throws DataNotFoundException{

        return memberRepository.findById(memberDto.getId()).orElseThrow(()-> new DataNotFoundException("member not found..")).toDto();
    }
}
