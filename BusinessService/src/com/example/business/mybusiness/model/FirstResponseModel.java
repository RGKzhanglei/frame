package com.example.business.mybusiness.model;

import com.example.business.mybusiness.communication.model.BeanModel;
import com.example.business.mybusiness.communication.serializer.annotation.SerializeField;
import com.example.business.mybusiness.communication.serializer.annotation.SerializeType;

/**
 * Created by zhang.la on 2015/9/18.
 */
public class FirstResponseModel extends BeanModel {

    @SerializeField(type = SerializeType.Dynamic, index = 0)
    public String firstArgument = "hello world!";

    @SerializeField(type = SerializeType.Dynamic, index = 1)
    public String url = "www.baidu.com";

    @SerializeField(type = SerializeType.Boolean, index = 2)
    public boolean varBoolean = true;

    @SerializeField(type = SerializeType.Int10, index = 3)
    public int varInt = 102411;
}
