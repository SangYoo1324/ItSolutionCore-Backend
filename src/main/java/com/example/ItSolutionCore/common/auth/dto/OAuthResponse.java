package com.example.ItSolutionCore.common.auth.dto;

public interface OAuthResponse {

    // provider Name
    String getProvider();

    // id issued by provider
    String getProviderId();


    String getEmail();

    // actual name
    String getName();

    String getBusiness();

}
