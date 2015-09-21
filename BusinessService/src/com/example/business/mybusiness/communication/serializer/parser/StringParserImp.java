package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldAnnotationModel;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;
import com.example.business.mybusiness.communication.util.SerializeUtil;

/**
 * String 序列化/反序列化
 * Created by zhang.la on 2015/9/10.
 */
class StringParserImp extends AbstractParser {
    public static StringParserImp instance = new StringParserImp();

    private StringParserImp() {}

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        FieldAnnotationModel annotation = fieldInfo.getFieldAnnotationModel();

        String strValue = "";
        int readLength = 0;
        switch (annotation.type) {
            case Code2:
                readLength = 2;
                break;
            case Code3:
                readLength = 3;
                break;
            case Code4:
                readLength = 4;
                break;
            case Code8:
                readLength = 8;
                break;
            case Dynamic:
                readLength = reader.readInt(4);
                break;
            case Dynamic10:
                readLength = reader.readInt(10);
                break;
            default:
                throw new RuntimeException("Error SerializeType:"+annotation.type);
        }
        strValue = reader.readString(readLength);
        return strValue;
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {
        FieldAnnotationModel annotation = fieldInfo.getFieldAnnotationModel();
        String value = "";
        if(instance != null){
            value = (String)instance;
        }
        int writeLength = 0;
        switch (annotation.type) {
            case Code2:
                writeLength = 2;
                break;
            case Code3:
                writeLength = 3;
                break;
            case Code4:
                writeLength = 4;
                break;
            case Code8:
                writeLength = 8;
                break;
            case Dynamic:
                writeLength = 4;//这个长度表示的是，长度的长度
                break;
            case Dynamic10:
                writeLength = 10;//这个长度表示的是，长度的长度
                break;
            default:
                throw new RuntimeException("Field："+fieldInfo.getName()+" Error SerializeType:"+annotation.type);
        }

        switch (annotation.type) {
            case Code2:
            case Code3:
            case Code4:
            case Code8:
                writer.writeString(value, writeLength);
                break;
            case Dynamic:
            case Dynamic10:
                byte[] byteArr = SerializeUtil.getByteArrByStr(value, writer.getCharsetName());
                writer.writeInt(byteArr.length, writeLength);//
                writer.write(byteArr);
                break;
            default:
                throw new RuntimeException("Field："+fieldInfo.getName()+" Error SerializeType:"+annotation.type);
        }
    }
}
