package com.example.business.mybusiness.communication.model;

/**
 * Created by zhang.la on 2015/9/14.
 */
public class RequestEntity {
    private BeanModel requestBean;

    private static RequestEntity mInstance = new RequestEntity();

    private RequestEntity() {}

    public static RequestEntity getInstance() {
        return mInstance;
    }

    public BeanModel getRequestBean() {
        return requestBean;
    }

    public void setRequestBean(BeanModel requestBean) {
        this.requestBean = requestBean;
    }

    public String getServiceCode() {
        return requestBean.getServiceCode();
    }
}
