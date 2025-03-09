package com.bakirwebservice.securityservice.api.response;

import com.bakirwebservice.securityservice.constants.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "Status",
        "Error",
        "Description"
})
public class BaseResponse {

    @JsonProperty("Status")
    private String status = ResponseStatus.SUCCESS_STATUS;

    @JsonProperty("Error")
    private String error = ResponseStatus.SUCCESS;

    @JsonProperty("Description")
    private String description = ResponseStatus.SUCCESS;


    public BaseResponse(String description){
        this.description = description;
    }
}