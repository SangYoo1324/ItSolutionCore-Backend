package com.example.ItSolutionCore.common.auth.dto;

import java.util.Map;

public class GoogleResponse implements OAuthResponse{

/*     Raw response data get from 3rd pt
            So, attirbute (key, value pair) should be customized */
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {

        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getBusiness() {
         return attribute.get("business").toString();
    }
}
