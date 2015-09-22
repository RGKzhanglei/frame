package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

/**
 * 序列化/反序列化 虚函数
 * Created by zhang.la on 2015/9/9.
 */
abstract class AbstractParser {
    abstract public Object deserialize(SerializeReader reader, FieldModel fieldInfo);

    abstract public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception;
}
