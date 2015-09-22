package com.example.business.mybusiness.communication.model;

import java.io.Serializable;

/**
 * Created by zhang.la on 2015/9/16.
 */
public class SendResultModel implements Serializable{
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token = "";

}
