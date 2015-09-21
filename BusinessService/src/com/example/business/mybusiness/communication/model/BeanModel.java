package com.example.business.mybusiness.communication.model;

import java.io.Serializable;

/**
 * Created by zhang.la on 2015/9/8.
 */
public class BeanModel implements Serializable, Cloneable {
    protected String serviceCode = "";

    public BeanModel() {
    }

    public String getServiceCode() {
        return serviceCode;
    }
}
