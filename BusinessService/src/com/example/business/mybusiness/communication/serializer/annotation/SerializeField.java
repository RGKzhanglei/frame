package com.example.business.mybusiness.communication.serializer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhang.la on 2015/9/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface SerializeField {
    SerializeType type() default SerializeType.FixedLength;

    int index() default 0;
}
