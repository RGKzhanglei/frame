package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

/**
 * Enum 序列化/反序列化
 * Created by zhang.la on 2015/9/14.
 */
class EnumParserImp extends AbstractParser{
    public static EnumParserImp instance = new EnumParserImp();

    private EnumParserImp() {
    }

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        return null;
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {

    }
}
