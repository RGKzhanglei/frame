package com.example.business.mybusiness.communication.serializer.model;

import com.example.business.mybusiness.communication.serializer.annotation.SerializeField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 保存field信息
 * Created by zhang.la on 2015/9/9.
 */
public class FieldModel implements Comparable<FieldModel>{

    private String name;

    private Field field;

    private FieldAnnotationModel fieldAnnotationModel;

    private Class<?> fieldClass;//字段类型
    private Class<?> fieldParmClass;//泛型的实际类型

    public FieldModel(Class<?> fieldClass) {
        this.fieldClass = fieldClass;
    }

    public FieldModel(String name, Field field, FieldAnnotationModel fieldAnnotationModel, Class<?> fieldClass, Class<?> fieldParmClass, Method getMethod, Method setMethod, SerializeField serializeField) {
        this.name = name;
        this.field = field;
        this.fieldAnnotationModel = fieldAnnotationModel;
        this.fieldClass = fieldClass;
        this.fieldParmClass = fieldParmClass;
        this.fieldAnnotationModel = new FieldAnnotationModel(serializeField);

        if (getMethod != null) {
            getMethod.setAccessible(true);
        }
        if (setMethod != null) {
            setMethod.setAccessible(true);
        }

        if (field != null) {
            field.setAccessible(true);
        }
    }

    public FieldModel(String name, Field field, Method getMethod, Method setMethod, SerializeField fieldAnnotation) throws Exception {
        this.name = name;
        this.field = field;
        this.fieldAnnotationModel = new FieldAnnotationModel(fieldAnnotation);

        if (getMethod != null) {
            getMethod.setAccessible(true);
        }
        if (setMethod != null) {
            setMethod.setAccessible(true);
        }

        if (field != null) {
            field.setAccessible(true);
        }

        Type type = field.getGenericType();
        if (type instanceof Class<?>) {//
            this.fieldClass = (Class<?>) type;
            this.fieldParmClass = null;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pdType = (ParameterizedType) type;
            this.fieldClass = (Class<?>) pdType.getRawType();
            this.fieldParmClass = (Class<?>) pdType.getActualTypeArguments()[0];
        } else {
            throw new Exception("not support filed type!");
        }
    }

    @Override
    public int compareTo(FieldModel another) {
        int thisIndex = this.fieldAnnotationModel.index;
        int anoterIndex = another.fieldAnnotationModel.index;
        if (thisIndex > anoterIndex) {
            return 1;
        } else if (thisIndex < anoterIndex) {
            return -1;
        } else {
            return 0;
        }
    }

    public void setAccessible(boolean flag) throws SecurityException {
        field.setAccessible(flag);
    }

    public FieldAnnotationModel getFieldAnnotationModel() {
        return fieldAnnotationModel;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public Class<?> getFieldClass() {
        return fieldClass;
    }

    public Class<?> getFieldParamClass() {
        return fieldParmClass;
    }

    public Object get(Object instance) throws IllegalAccessException {
        return field.get(instance);
    }

    public void set(Object javaObject, Object value) throws IllegalAccessException, InvocationTargetException {
        field.set(javaObject, value);
    }
}
