package com.example.business.mybusiness.communication.model;

/**
 * response
 * Created by zhang.la on 2015/9/14.
 */
public class ResponseEntity {
    public static final String RESPONSE_SUCCESSED = "0";
    public static final String RESPONSE_CANCELED = "1";
    public static final String RESPONSE_FAILED = "2";
    private String responseState = RESPONSE_SUCCESSED;

    private BeanModel reponseBean;

    private static ResponseEntity mInstance = new ResponseEntity();

    private ResponseEntity() {

    }

    public static ResponseEntity getInstance() {
        return mInstance;
    }

    public String getResponseState() {
        return responseState;
    }

    public void setResponseState(String responseState) {
        this.responseState = responseState;
    }

    public BeanModel getReponseBean() {
        return reponseBean;
    }

    public void setReponseBean(BeanModel reponseBean) {
        this.reponseBean = reponseBean;
    }
}
