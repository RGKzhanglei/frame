package com.example.business.mybusiness.communication.serializer.model;

import com.example.business.mybusiness.communication.serializer.annotation.SerializeField;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class构建fieldList
 * Created by zhang.la on 2015/9/11.
 */
public class ClassModel {
    private final Class<?> clazz;
    private final List<FieldModel> fieldList = new ArrayList<FieldModel>();
    private Constructor<?> defaultConstructor;

    public ClassModel(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public List<FieldModel> getFieldList() {
        return fieldList;
    }


    public Constructor<?> getDefaultConstructor() {
        return defaultConstructor;
    }

    public void setDefaultConstructor(Constructor<?> defaultConstructor) {
        this.defaultConstructor = defaultConstructor;
    }

    /**
     *
     * @param clazz
     * @return
     */
    public static ClassModel computeSetters(Class<?> clazz) {
        ClassModel beanInfo = new ClassModel(clazz);

        Constructor<?> defaultConstructor = getDefaultConstructor(clazz);
        if (defaultConstructor != null) {
            defaultConstructor.setAccessible(true);
            beanInfo.setDefaultConstructor(defaultConstructor);
        } else if (defaultConstructor == null && !(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()))) {
            throw new IllegalArgumentException("default constructor not found. " + clazz);
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            SerializeField fieldAnnotation = field.getAnnotation(SerializeField.class);
            if (fieldAnnotation != null) {
                String propertyName = field.getName();
                String methodName = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
                Method getMethod = null;
                Method setMethod = null;
                try {
                    beanInfo.add(new FieldModel(propertyName, field, getMethod, setMethod, fieldAnnotation));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
        }
        //排序
        Collections.sort(beanInfo.getFieldList());
        return beanInfo;
    }

    /**
     * 默认构造函数
     * @param clazz
     * @return
     */
    public static Constructor<?> getDefaultConstructor(Class<?> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }

        Constructor<?> defaultConstructor = null;
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                defaultConstructor = constructor;
                break;
            }
        }
        return defaultConstructor;
    }

    public boolean add(FieldModel field) {
        for (FieldModel item : this.fieldList) {
            if (item.getName().equals(field.getName())) {
                return false;
            }
        }
        fieldList.add(field);

        return true;
    }

}
