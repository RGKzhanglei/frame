package com.example.business.mybusiness.communication.serializer.model;

import com.example.business.mybusiness.communication.serializer.annotation.SerializeField;
import com.example.business.mybusiness.communication.serializer.annotation.SerializeType;

/**
 * 注解model
 * Created by zhang.la on 2015/9/10.
 */
public class FieldAnnotationModel {
    int index = -1;
    public SerializeType type = SerializeType.FixedLength;
    public int length = 0;

    public FieldAnnotationModel(SerializeField serializeField) {
        this.index = serializeField.index();
        this.type = serializeField.type();
    }
}
