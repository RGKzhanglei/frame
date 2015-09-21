package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

/**
 * Created by zhang.la on 2015/9/14.
 */
class PriceParserImp extends AbstractParser {
    public static PriceParserImp instance = new PriceParserImp();

    private PriceParserImp() {
    }

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        return null;
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) throws Exception {

    }
}
