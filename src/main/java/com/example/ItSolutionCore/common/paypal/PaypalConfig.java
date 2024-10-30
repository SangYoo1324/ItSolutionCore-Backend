package com.example.ItSolutionCore.common.paypal;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {
    //paypal
    @Value("${transaction.paypal.client.id}")
    private String clientId;
    @Value("${transaction.paypal.client.secret}")
    private String clientSecret;
    @Value("${transaction.paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId,clientSecret,mode);
    }

}
