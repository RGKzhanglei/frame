package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldAnnotationModel;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

/**
 * Created by zhang.la on 2015/9/14.
 */
class BooleanParserImp extends AbstractParser{
    public static BooleanParserImp instance = new BooleanParserImp();

    private BooleanParserImp() {
    }

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {

        FieldAnnotationModel annotation = fieldInfo.getFieldAnnotationModel();
        String valueStr = "";
        int readLength = 1;

        valueStr = reader.readString(readLength);

        if ("1".equals(valueStr)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {
        boolean value = false;
        if (instance != null) {
            value = ((Boolean) instance).booleanValue();
        }

        int writeLength =1;

        if (value) {
            writer.writeString("1", writeLength);
        } else {
            writer.writeString("0", writeLength);
        }
    }
}
