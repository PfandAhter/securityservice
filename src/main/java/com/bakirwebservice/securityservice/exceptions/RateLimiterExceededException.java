package com.bakirwebservice.securityservice.exceptions;

import lombok.Getter;
import org.apache.catalina.util.RateLimiter;

public class RateLimiterExceededException extends RuntimeException{

    @Getter
    private String message;

    public RateLimiterExceededException(){
        super();
        this.message = null;
    }

    public RateLimiterExceededException(String message){
        super(message);
        this.message = message;
    }
}
