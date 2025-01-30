package com.bakirwebservice.securityservice.exceptions;

public class AccessDeniedException extends Exception{

    private final String message;

    public AccessDeniedException( ){
        super();
        this.message = null;
    }
    public AccessDeniedException(String message){
        super();
        this.message = message;
    }

}
