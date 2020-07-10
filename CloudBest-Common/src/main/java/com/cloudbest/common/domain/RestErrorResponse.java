package com.cloudbest.common.domain;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data
public class RestErrorResponse {

    private String errCode;

    private String errMessage;

    public RestErrorResponse(String errCode,String errMessage){
        this.errCode = errCode;
        this.errMessage= errMessage;
    }


}
