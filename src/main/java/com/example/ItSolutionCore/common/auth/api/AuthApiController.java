package com.example.ItSolutionCore.common.auth.api;


import com.example.ItSolutionCore.common.auth.service.AuthService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.dto.MemberDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.util.OriginDeterminer;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthApiController {


    private final AuthService authService;

    private final OriginDeterminer originDeterminer;


    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDto memberDto,  @CookieValue(value = "origin", defaultValue = "itSolution") String origin){
            log.info("origin::"+origin);
        try {
           // use token as a return value so the frontend can utilize it as param
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericResponseDto.builder().response( authService.Signup(memberDto, origin)).build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericResponseDto.builder().response("error occured. Cannot find business with business_id").build());
        }


    }

    @GetMapping("/api/signup/verification")
    public void signupVerification(@RequestParam("token")String token,@RequestParam("origin") String origin_address, HttpServletResponse response) {


        log.info(token);

        try {
            authService.Signup_email_verify(token);
            response.sendRedirect(origin_address+"/login");

        } catch (Exception e) {
            e.printStackTrace();
            // fix later when made appropriate error page

        }
    }






    /*
    * OAuth2 login link. must include origin to be redirected to the intended business frontend website.
    * */
    @GetMapping("/api/public/google/login/{origin}")  // origin= itSolution, sunriseCC, etc...
    public void googleOAuth2login(HttpServletResponse response, @PathVariable String origin, HttpSession session){

        // let httpSession store the origin with key business. it will be utilized to set redirection url
            session.setAttribute("business", origin);
            log.info("session info from API :::"+session.getAttribute("business").toString());

        try {
            response.sendRedirect(originDeterminer.OAuth2OriginDeterminer(origin)+"/oauth2/authorization/google");
        } catch (IOException e) {
           e.printStackTrace();
        }
    }


    @GetMapping("api/auth/user/test")
    public ResponseEntity<?> userTest(){
        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Authenticateduser API test").build());
    }

    @GetMapping("api/admin/test")
    public ResponseEntity<?> adminTest(){
        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Admin Ahtorization API test").build());
    }


}
