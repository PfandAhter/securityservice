package com.bakirwebservice.securityservice.config;


import com.bakirwebservice.securityservice.exceptions.decoder.CustomFeignErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper){
        return new CustomFeignErrorDecoder(objectMapper);
    }
}