package com.example.ItSolutionCore.common.auth.service;


import com.example.ItSolutionCore.common.auth.dto.CustomOAuth2User;
import com.example.ItSolutionCore.common.auth.dto.GoogleResponse;
import com.example.ItSolutionCore.common.auth.dto.OAuthResponse;
import com.example.ItSolutionCore.common.auth.dto.AuthMemberDto;
import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.repo.BusinessRepository;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import com.example.ItSolutionCore.common.util.OriginDeterminer;
import com.example.ItSolutionCore.enums.Role;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {


    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    private final OriginDeterminer originDeterminer;

    private final BusinessRepository businessRepository;

    // user request provided by the 3rd party login

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // getting business info from httpSession
        String businessName =(String) httpSession.getAttribute("business");
        Business business = null;
        if(businessName!=null){
            log.info("Business identified. fetching business entity from db");

            try {
              business=  businessRepository.findByName_onlyBusiness(businessName).orElseThrow(()->new DataNotFoundException("Cannot find business with businessName"));
            } catch (DataNotFoundException e) {
                log.error("cannot find business entity from DB... Setting business null");

                throw new RuntimeException(e);
            }

        }else{
            log.error("no business info included... Setting business null");
        }


        log.info("oAuth2User before super.loadUser(userRequest)::::: {}",userRequest.toString());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User after super.loadUser(userRequest)::::: {}",oAuth2User.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuthResponse oAuthResponse = null;

        String provider = null;

        if(registrationId.equals("google")){
            oAuthResponse = new GoogleResponse(oAuth2User.getAttributes());
            provider = "google";
        }
        else{
            return null;
        }

        String username = oAuthResponse.getProvider()+ " "+ oAuthResponse.getProviderId();

        try {
            Member existedUser = memberRepository.findByUsername(username).orElseThrow(()-> new DataNotFoundException("member not found"));


        } catch (DataNotFoundException e) { // if it's new user, add to DB
            Member newMember = Member.builder()
                    .authority(Role.ROLE_USER)
                    .username(username)
                    .name(oAuthResponse.getName())
                    .email(oAuthResponse.getEmail())
                    .provider(oAuthResponse.getProvider())
                    .business(business)
                    .verified(true)
                    .build();

            memberRepository.save(newMember);
        }
        AuthMemberDto authMemberDto = AuthMemberDto.builder()
                .email(oAuthResponse.getEmail())
                .name(oAuthResponse.getName())
                .role(Role.ROLE_USER)
                .username(username)
                .provider(provider)
                .build();
        return new CustomOAuth2User(authMemberDto);
    }
}
