package com.bakirwebservice.securityservice.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {

    private String message;
    private String errorCode;
    private Object details;


    public String getFormattedMessage(Object... args){
        String result = message;

        if (args != null && args.length > 0) {
            for(int i = 0; i < args.length; i++){
                result = result.replace("%dynamicvalue" + (i+1) + "%", String.valueOf(args[i]));
            }
        }
        return result;
    }
}