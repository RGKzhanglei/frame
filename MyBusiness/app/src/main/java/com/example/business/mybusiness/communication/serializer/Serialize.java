package com.example.business.mybusiness.communication.serializer;

import android.util.Log;

import com.example.business.mybusiness.communication.serializer.model.FieldModel;
import com.example.business.mybusiness.communication.serializer.parser.ParserConfig;

/**
 * 序列化/反序列化的入口类
 * Created by zhang.la on 2015/9/8.
 */
public class Serialize {
    public static final String TAG = "Serialize";

    public static final String charsetName = "GBK";
    public static final String charsetName_UTF8 = "UTF-8";
    public static final String charsetName_ASCII = "ASCII";

    public static final Object deserialize(byte[] data, Class<?> type, String charsetName) {
        SerializeReader serializeReader = new SerializeReader(charsetName, data);
        FieldModel fieldModel = new FieldModel(type);
        Object retObj = null;
        retObj = ParserConfig.getInstance().deserialize(serializeReader, fieldModel);
        return retObj;
    }

    /**
     * 序列化request/response
     * @param object
     * @param charsetName
     * @return
     */
    public static final byte[] serialize(Object object, String charsetName) {
        Class<?> type = object.getClass();
        SerializeWriter writer = new SerializeWriter(100, charsetName);
        FieldModel fieldModel = new FieldModel(type);

        try {
            ParserConfig.getInstance().serialize(writer, fieldModel, object);
        } catch (Exception e) {
            Log.e(TAG, "序列化出错:" + e);
        }
        return writer.toByteArr();
    }
}
