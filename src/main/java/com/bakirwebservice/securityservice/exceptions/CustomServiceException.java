package com.bakirwebservice.securityservice.exceptions;

import lombok.Getter;

public class CustomServiceException extends RuntimeException{

    @Getter
    private final String message;

    @Getter
    private final int statusCode;

    @Getter
    private final String errorCode;

    public CustomServiceException(String message, String errorCode, int statusCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }
}