package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

/**
 * Created by zhang.la on 2015/9/14.
 */
class TimeParserImp extends AbstractParser{
    public static TimeParserImp instance = new TimeParserImp();

    private TimeParserImp() {
    }

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        return null;
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {

    }
}
