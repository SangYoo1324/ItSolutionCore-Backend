package com.example.ItSolutionCore.common.auth.service;

import com.example.ItSolutionCore.common.auth.dto.CustomUserDetails;
import com.example.ItSolutionCore.common.auth.dto.AuthMemberDto;
import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Iterator;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        log.info("CustomUserDetailsService::"+ username);

        List<Member> members = memberRepository.findAll();
        for(Member m: members){
            log.info("member- "+m.getUsername());
        }

        Member member = memberRepository.findByUsername(username).orElse(null);

        if(member !=null ){
            if(!member.isVerified()){
                log.error("Member email address not verified...");
                return null;
            }
            log.info("Member found:: "+ member.getUsername());
           AuthMemberDto authMember =  member.toAuthMemberDto();
            UserDetails userDetail = this.createUserDetails(authMember);
            log.info("CustomUserDetailService made UserDetail Object which is AuthManager understandable. it's granted authorities as below.. ");
            userDetail.getAuthorities().stream().forEach(e->log.info(e.toString()));
            return userDetail;
        }
        else {
            log.error("userinfo not found by CustomUserDetailService...");
            return null;
        }




    }

    private UserDetails createUserDetails(AuthMemberDto authMemberDto){
        log.info("member Dto::::"+ authMemberDto);
        return new CustomUserDetails(authMemberDto);
    }
}
