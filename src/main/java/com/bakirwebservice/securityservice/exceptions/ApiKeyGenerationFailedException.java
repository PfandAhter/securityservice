package com.bakirwebservice.securityservice.exceptions;

import lombok.Getter;

public class ApiKeyGenerationFailedException extends RuntimeException {

    @Getter
    private String message;

    public ApiKeyGenerationFailedException(){
        super();
        this.message = null;
    }

    public ApiKeyGenerationFailedException(String message) {
        super(message);
        this.message = message;
    }
}
