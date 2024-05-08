package com.example.ItSolutionCore.common.util;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OriginDeterminer {
    @Value("${redirection.url.itSolution}")
    private String itSolution_url;
    @Value("${redirection.url.sunriseCC}")
    private String sunriseCC_url;

    /*
     * add more sub)services as needed
     * */
    public String OAuth2OriginDeterminer(String origin){
        switch (origin){

            case "itSolution":
                return itSolution_url;

            case "sunriseCC":
                return sunriseCC_url;

            default: return null;
        }
    }

}
