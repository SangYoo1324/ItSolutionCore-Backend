package com.example.ItSolutionCore.common.api;


import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.dto.MemberDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    // fetching userProfile for profile tab
    @PostMapping("/api/member")
    public ResponseEntity<?> fetchUserInfo(@RequestParam("jwtToken") String jwtToken){
        try {
            return ResponseEntity.status(HttpStatus.OK).body( memberService.fetchUserInfo(jwtToken));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("Member Not found or jwt token is not valid..."));
        }
    }



}
