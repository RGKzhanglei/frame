package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

import java.util.ArrayList;

/**
 * Created by zhang.la on 2015/9/14.
 */
class ArrayListParserImp extends AbstractParser{
    public static ArrayListParserImp instance = new ArrayListParserImp();

    private ArrayListParserImp() {
    }

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        int count = reader.readInt(4);
        if (count == 0) {
            return null;
        }
        ArrayList retlist = new ArrayList(count);
        AbstractParser parser = BeanParserImp.instance;
        FieldModel fieldModelTemp = new FieldModel(fieldInfo.getFieldParamClass());
        for (int i = 0; i < count; i++) {
            Object obj = parser.deserialize(reader, fieldModelTemp);
            retlist.add(obj);
        }
        return retlist;
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {
        if (instance == null) {
            writer.writeInt(0, 4);
            return;
        }
        ArrayList list = (ArrayList) instance;
        writer.writeInt(list.size(), 4);
        AbstractParser parser = BeanParserImp.instance;
        FieldModel fieldModelTemp = new FieldModel(fieldInfo.getFieldParamClass());
        for (Object object : list) {
            parser.serialize(writer, fieldModelTemp, object);
        }
    }
}
