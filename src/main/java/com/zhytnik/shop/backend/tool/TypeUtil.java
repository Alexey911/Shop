package com.zhytnik.shop.backend.tool;

import com.google.common.collect.ImmutableMap;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.exception.InfrastructureException;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.type.*;

import java.sql.Types;
import java.util.Date;
import java.util.Map;

import static com.zhytnik.shop.backend.validator.LengthValidator.MAX_LENGTH;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class TypeUtil {

    private static Map<PrimitiveType, Type> TYPE_CONVERTER;
    private static Map<PrimitiveType, Integer> TYPE_MAPPING;
    private static Map<PrimitiveType, Class> TYPE_REFLECTION;

    static {
        TYPE_MAPPING = ImmutableMap.<PrimitiveType, Integer>builder().
                put(PrimitiveType.LONG, Types.BIGINT).
                put(PrimitiveType.TEXT, Types.VARCHAR).
                put(PrimitiveType.DOUBLE, Types.DOUBLE).
                put(PrimitiveType.BOOLEAN, Types.BOOLEAN).
                put(PrimitiveType.STRING, Types.BIGINT).
                put(PrimitiveType.DATE, Types.DATE).build();

        TYPE_REFLECTION = ImmutableMap.<PrimitiveType, Class>builder().
                put(PrimitiveType.LONG, Long.class).
                put(PrimitiveType.TEXT, String.class).
                put(PrimitiveType.DOUBLE, Double.class).
                put(PrimitiveType.BOOLEAN, Boolean.class).
                put(PrimitiveType.DATE, Date.class).build();

        TYPE_CONVERTER = ImmutableMap.<PrimitiveType, Type>builder().
                put(PrimitiveType.LONG, LongType.INSTANCE).
                put(PrimitiveType.STRING, LongType.INSTANCE).
                put(PrimitiveType.TEXT, StringType.INSTANCE).
                put(PrimitiveType.DOUBLE, DoubleType.INSTANCE).
                put(PrimitiveType.BOOLEAN, BooleanAdapter.BOOLEAN_ADAPTER).
                put(PrimitiveType.DATE, DbTimestampType.INSTANCE).build();
    }

    private TypeUtil() {
    }

    public static String getSqlType(Dialect dialect, PrimitiveType type) {
        final Integer code = TYPE_MAPPING.get(type);
        if (code == null) failOnUnknownType(type);
        return (type == PrimitiveType.TEXT) ?
                dialect.getTypeName(code, MAX_LENGTH, 0, 0) :
                dialect.getTypeName(code);
    }

    public static StringRepresentableType getTypeConverter(PrimitiveType type) {
        final Type nativeType = TYPE_CONVERTER.get(type);
        if (nativeType == null) failOnUnknownType(type);
        return (StringRepresentableType) nativeType;
    }

    public static Class getNativeClass(PrimitiveType type) {
        final Class clazz = TYPE_REFLECTION.get(type);
        if (clazz == null) failOnUnknownType(type);
        return clazz;
    }

    private static String failOnUnknownType(PrimitiveType type) {
        throw new InfrastructureException("unsupported.type", type);
    }

    private static class BooleanAdapter extends BooleanType {

        public static final BooleanAdapter BOOLEAN_ADAPTER = new BooleanAdapter();

        @Override
        public Boolean fromStringValue(String value) throws HibernateException {
            if (value != null && value.equals("1")) return true;
            return super.fromStringValue(value);
        }
    }
}
