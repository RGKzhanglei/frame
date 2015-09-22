package com.example.business.mybusiness.communication.model;

import java.io.Serializable;

/**
 * Created by zhang.la on 2015/9/15.
 */
public class ResponseModel implements Serializable {
    private String token = "";
    private String responseState = ResponseEntity.RESPONSE_SUCCESSED;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResponseState() {
        return responseState;
    }

    public void setResponseState(String responseState) {
        this.responseState = responseState;
    }
}
