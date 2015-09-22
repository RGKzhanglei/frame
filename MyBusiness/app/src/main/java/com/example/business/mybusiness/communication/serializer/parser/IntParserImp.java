package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldAnnotationModel;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;
import com.example.business.mybusiness.communication.util.SerializeUtil;

/**
 * int 序列化/反序列化
 * Created by zhang.la on 2015/9/10.
 */
class IntParserImp extends AbstractParser {
    public static IntParserImp instance = new IntParserImp();

    private IntParserImp() {}

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        FieldAnnotationModel fieldAnnotationModel = fieldInfo.getFieldAnnotationModel();
        String valueStr = "";
        int readLength = 0;
        switch (fieldAnnotationModel.type) {
            case Int4:
                readLength = 4;
                break;
            case Int10:
                readLength = 10;
                break;
            case Int20:
                readLength = 20;
                break;
            case Dynamic://兼容部分变长的int
                readLength = reader.readInt(4);
                break;
            default:
                throw new RuntimeException("Error SerializeType:" + fieldAnnotationModel.type);
        }
        valueStr = reader.readString(readLength);

        if ("".equals(valueStr) && fieldInfo.getFieldClass().isPrimitive()) {
            return 0;
        }

        return Integer.parseInt(valueStr);
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {
        FieldAnnotationModel annotation = fieldInfo.getFieldAnnotationModel();

        int valueInt = 0;
        if (instance != null) {
            valueInt = ((Integer) instance).intValue();
        }
        String valueStr = valueInt + "";

        int writeLength = 0;
        switch (annotation.type) {
            case Int4:
                writeLength = 4;
                break;
            case Int10:
                writeLength = 10;
                break;
            case Int20:
                writeLength = 20;
                break;
            case Dynamic://兼容部分变长的int
                writeLength = 4;//这个长度表示的是，长度的长度
                break;
            default:
                throw new Exception("Field：" + fieldInfo.getName() + " Error SerializeType:" + annotation.type);
        }

        switch (annotation.type) {
            case Int4:
            case Int10:
            case Int20:
                writer.writeString(valueStr, writeLength);
                break;
            case VariableLength:
            case Dynamic://兼容部分变长的int
                byte[] byteArr = SerializeUtil.getByteArrByStr(valueStr, writer.getCharsetName());
                writer.writeInt(byteArr.length, writeLength);
                writer.write(byteArr);
                break;
            default:
                throw new Exception("Field：" + fieldInfo.getName() + " Error SerializeType:" + annotation.type);
        }
    }
}
