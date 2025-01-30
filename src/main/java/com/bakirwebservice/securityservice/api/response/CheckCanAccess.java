package com.bakirwebservice.securityservice.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CheckCanAccess extends BaseResponse {

    private Boolean canAccess = Boolean.TRUE;

}
