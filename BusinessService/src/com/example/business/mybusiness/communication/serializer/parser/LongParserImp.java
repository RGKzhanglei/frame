package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

/**
 * Created by zhang.la on 2015/9/14.
 */
class LongParserImp extends AbstractParser {
    public static LongParserImp instance = new LongParserImp();

    private LongParserImp() {}

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        String valueStr = "";
        int readLength = 20;
        valueStr = reader.readString(readLength);

        if ("".equals(valueStr) && fieldInfo.getFieldClass().isPrimitive()) {
            return 0L;
        }
        return Long.parseLong(valueStr);
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {
        long valueLong = 0;
        if (instance != null) {
            valueLong = ((Long) instance).longValue();
        }
        String valueStr = valueLong + "";

        int writeLength = 20;

        writer.writeString(valueStr, writeLength);
    }
}
