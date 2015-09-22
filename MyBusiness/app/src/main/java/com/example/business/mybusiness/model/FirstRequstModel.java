package com.example.business.mybusiness.model;

import com.example.business.mybusiness.communication.model.BeanModel;
import com.example.business.mybusiness.communication.serializer.annotation.SerializeField;
import com.example.business.mybusiness.communication.serializer.annotation.SerializeType;

/**
 * Created by zhang.la on 2015/9/18.
 */
public class FirstRequstModel extends BeanModel {

    @SerializeField(type = SerializeType.Dynamic, index = 0)
    public String firstArgument = "12";

    @SerializeField(type = SerializeType.Boolean, index = 1)
    public boolean varBoolean = false;

    @SerializeField(type = SerializeType.Int4, index = 2)
    public int varInt = 356;

    @SerializeField(type = SerializeType.Dynamic, index = 3)
    public String url = "123";

    public FirstRequstModel() {
        serviceCode = "110001";
    }
}
