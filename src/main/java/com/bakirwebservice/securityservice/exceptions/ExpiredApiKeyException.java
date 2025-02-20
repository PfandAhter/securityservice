package com.bakirwebservice.securityservice.exceptions;

import lombok.Getter;

public class ExpiredApiKeyException extends RuntimeException{

    @Getter
    private String message;

    public ExpiredApiKeyException(){
        super();
        this.message = null;
    }

    public ExpiredApiKeyException(String message){
        super(message);
        this.message = message;
    }
}
