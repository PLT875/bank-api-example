package com.starling.infrastructure.starlingapi;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class StarlingApiClientConfig {

    /**
     * The request interceptor forwards the authorization header to the Starling API Feign client
     */
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                template.header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
            }
        };
    }

    @Bean
    public StarlingApiClient starlingApiClient(
            @Value("${starling.api.base-url}") String baseUrl,
            @Value("${starling.api.log-level:NONE}") String logLevel
    ) {
        return Feign.builder()
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logLevel(Logger.Level.valueOf(logLevel))
                .requestInterceptor(requestInterceptor())
                .target(StarlingApiClient.class, baseUrl);
    }

}
