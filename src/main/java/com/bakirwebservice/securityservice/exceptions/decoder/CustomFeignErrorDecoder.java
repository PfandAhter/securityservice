package com.bakirwebservice.securityservice.exceptions.decoder;

import com.bakirwebservice.securityservice.exceptions.CustomServiceException;
import com.bakirwebservice.securityservice.model.pojo.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    private final ErrorDecoder defaultErrorCoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        try{
            String responseBody = getResponseBody(response);

            log.error("Feign error response: {} - {}", response.status(),responseBody);

            /*if(response.status() == HttpStatus.PRECONDITION_FAILED.value()){
                ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            }*/

            if (response.status() >= 500) {
                return new CustomServiceException(
                        "Remote service error occurred",
                        "SYSTEM_ERROR",
                        response.status()
                );
            }
            return defaultErrorCoder.decode(methodKey, response);
        }
        catch (Exception e){
            log.error("Error decoding feign response", e);
            return new CustomServiceException(
                    "Error processing service response",
                    "DECODE_ERROR",
                    response.status()
            );
        }
    }

    private String getResponseBody(Response response)throws IOException {
        try(InputStream inputBody = response.body().asInputStream()){
            return new String(inputBody.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}