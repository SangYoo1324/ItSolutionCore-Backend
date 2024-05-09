package com.example.ItSolutionCore.common.config;


import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletContainerCustomization {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> cookieCustomizer() {
        return factory -> {
            factory.addInitializers(servletContext -> {
                servletContext.getSessionCookieConfig().setName("JSESSIONID");
                servletContext.getSessionCookieConfig().setDomain("www.ps-its.com");
                servletContext.getSessionCookieConfig().setPath("/");
                servletContext.getSessionCookieConfig().setHttpOnly(false);
                servletContext.getSessionCookieConfig().setSecure(false);
            });
        };
    }

}
