package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.model.FieldAnnotationModel;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

import java.lang.reflect.Field;

/**
 * 序列化/反序列化 config
 * Created by zhang.la on 2015/9/10.
 */
public class ParserConfig {
    private static ParserConfig ourInstance = new ParserConfig();

    public static ParserConfig getInstance() {
        return ourInstance;
    }

    private ParserConfig() {
    }

    public static AbstractParser getParser(FieldAnnotationModel fieldAnnotationModel, Field field) {
        AbstractParser parser = null;
        switch (fieldAnnotationModel.type) {
            case Code2:
            case Code3:
            case Code4:
            case Code8:
            case Dynamic:
            case Dynamic10:
                parser = StringParserImp.instance;
                break;
            case Int4:
            case Int10:
                parser = IntParserImp.instance;
                break;
            case Int20:
                parser = LongParserImp.instance;
                break;
            case Decimal:
            case Decimal1:
            case Decimal2:
            case Decimal6:
                parser = DecimalParserImp.instance;
                break;
            case List:
                parser = ArrayListParserImp.instance;
                break;
            case Class:
            case NullableClass:
                parser = BeanParserImp.instance;
                break;
            case Enum:
                parser = EnumParserImp.instance;
                break;
            case EnumB:
                parser = EnumArrParserImp.instance;
                break;
            case Boolean:
                parser = BooleanParserImp.instance;
                break;
            case Date:
            case Time:
            case DateTime:
                parser = TimeParserImp.instance;
                break;
            case Price:
                parser = PriceParserImp.instance;
                break;
            case ByteArray:
                parser = ByteArrParserImp.instance;
                break;
            case Default:
                break;
            default:
                throw new RuntimeException("nonsupport Deserializer for type:" + fieldAnnotationModel.type);
        }
        return parser;
    }

    /**
     * request / response 序列化
     * @param writer
     * @param fieldInfo
     * @param instance
     */
    public static void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) {
        BeanParserImp.instance.serialize(writer, fieldInfo, instance);
    }

    /**
     * request / response 反序列化
     * @param reader
     * @param fieldInfo
     * @return
     */
    public static Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        return BeanParserImp.instance.deserialize(reader, fieldInfo);
    }
}
