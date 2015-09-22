package com.example.business.mybusiness.communication.serializer.annotation;

/**
 * Created by zhang.la on 2015/9/10.
 */
public enum SerializeType {
    @Deprecated
    FixedLength,
    @Deprecated
    VariableLength,
    @Deprecated
    OldList,
    @Deprecated
    Default,
    Code2,
    Code3,
    Code4,
    Code8,
    Dynamic,
    Dynamic10,
    Int4,
    Int10,
    Int20,
    Decimal,
    Decimal6,
    Decimal2,
    Decimal1,
    List,
    Class,
    NullableClass,
    Enum,
    EnumB,
    Boolean,
    Date,
    Time,
    DateTime,
    Price,
    ByteArray
}
