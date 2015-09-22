package com.example.business.mybusiness.communication.serializer.parser;

import com.example.business.mybusiness.communication.serializer.SerializeReader;
import com.example.business.mybusiness.communication.serializer.SerializeWriter;
import com.example.business.mybusiness.communication.serializer.annotation.SerializeType;
import com.example.business.mybusiness.communication.serializer.model.ClassModel;
import com.example.business.mybusiness.communication.serializer.model.FieldAnnotationModel;
import com.example.business.mybusiness.communication.serializer.model.FieldModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * model 序列化/反序列化
 * Created by zhang.la on 2015/9/9.
 */
class BeanParserImp extends AbstractParser {

    public static BeanParserImp instance = new BeanParserImp();

    private BeanParserImp() {}

    @Override
    public Object deserialize(SerializeReader reader, FieldModel fieldInfo) {
        FieldAnnotationModel fieldAnnotationModel = fieldInfo.getFieldAnnotationModel();
        if (null != fieldAnnotationModel && fieldAnnotationModel.type == SerializeType.NullableClass) {
            int b = reader.readInt(4);
            if (0 == b) {
                return null;
            }
        }

        ClassModel classModel = ClassModel.computeSetters(fieldInfo.getFieldClass());
        Object instance = null;
        try {
            instance = classModel.getDefaultConstructor().newInstance();  //JDC 1.6 如果底层构造方法所需的参数为0，则所提供的initargs数组的长度可能为0或null
        } catch (Exception e) {
            throw new RuntimeException("New Instance Fail!",e);
        }

        List<FieldModel> modelList = classModel.getFieldList();
        for (FieldModel fieldModel : modelList) {
            FieldAnnotationModel fieldAnnotationModel1 = fieldModel.getFieldAnnotationModel();
            Object object = null;
            try {
                AbstractParser parser = ParserConfig.getInstance().getParser(fieldAnnotationModel1, fieldModel.getField());
                object = parser.deserialize(reader, fieldModel);
                if (null != object) {
                    fieldModel.setAccessible(true);
                    fieldModel.set(instance, object);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public void serialize(SerializeWriter writer, FieldModel fieldInfo, Object instance) {
        FieldAnnotationModel fieldAnnotationModel = fieldInfo.getFieldAnnotationModel();
        if (null != fieldAnnotationModel && fieldAnnotationModel.type == SerializeType.NullableClass) {
            if (null == instance) {
                writer.writeString("0", 4);
                return;
            } else {
                writer.writeString("1", 4);
            }
        }

        if(instance == null){
            throw new IllegalArgumentException(fieldInfo.getName() + " can't be null!");
        }

        ClassModel classModel = ClassModel.computeSetters(fieldInfo.getFieldClass());
        List<FieldModel> fields = classModel.getFieldList();
        for (FieldModel fieldModel : fields) {
            Object value = null;
            try {
                fieldModel.setAccessible(true);
                value = fieldModel.get(instance);
                AbstractParser nextParser = ParserConfig.getInstance().getParser(fieldModel.getFieldAnnotationModel(), fieldModel.getField());
                nextParser.serialize(writer, fieldModel, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
