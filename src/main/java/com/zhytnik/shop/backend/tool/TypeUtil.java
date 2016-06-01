package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import org.hibernate.type.*;

import java.sql.Types;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class TypeUtil {

    private static Map<PrimitiveType, Integer> TYPE_MAPPING;
    private static Map<PrimitiveType, Type> TYPE_CONVERTER;

    static {
        TYPE_MAPPING = newHashMap();
        TYPE_MAPPING.put(PrimitiveType.LONG, Types.BIGINT);
        TYPE_MAPPING.put(PrimitiveType.STRING, Types.CHAR);
        TYPE_MAPPING.put(PrimitiveType.DOUBLE, Types.DOUBLE);
        TYPE_MAPPING.put(PrimitiveType.BOOLEAN, Types.BOOLEAN);
        TYPE_MAPPING.put(PrimitiveType.DATE, Types.DATE);

        TYPE_CONVERTER = newHashMap();
        TYPE_CONVERTER.put(PrimitiveType.LONG, LongType.INSTANCE);
        TYPE_CONVERTER.put(PrimitiveType.STRING, StringType.INSTANCE);
        TYPE_CONVERTER.put(PrimitiveType.DOUBLE, DoubleType.INSTANCE);
        TYPE_CONVERTER.put(PrimitiveType.BOOLEAN, BooleanType.INSTANCE);
        TYPE_CONVERTER.put(PrimitiveType.DATE, DateType.INSTANCE);
    }

    private TypeUtil() {
    }

    public static Integer getSqlTypeCode(PrimitiveType type) {
        return TYPE_MAPPING.get(type);
    }

    public static Type getTypeConverter(PrimitiveType type) {
        return TYPE_CONVERTER.get(type);
    }
}
