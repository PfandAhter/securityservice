package com.bakirwebservice.securityservice.exceptions;

import lombok.Getter;

public class ProcessFailedException extends RuntimeException{

    @Getter
    private String message;


    public ProcessFailedException(){
        super();
        this.message = null;
    }

    public ProcessFailedException(String message){
        super(message);
        this.message = message;
    }
}