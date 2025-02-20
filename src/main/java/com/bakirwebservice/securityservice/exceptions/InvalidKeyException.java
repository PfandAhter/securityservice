package com.bakirwebservice.securityservice.exceptions;

import lombok.Getter;

public class InvalidKeyException extends RuntimeException{

    @Getter
    private String message;

    public InvalidKeyException(){
        super();
        this.message = null;
    }

    public InvalidKeyException(String message){
        super(message);
        this.message = message;
    }
}
