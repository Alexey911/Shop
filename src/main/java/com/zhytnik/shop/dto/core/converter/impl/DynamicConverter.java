package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.Dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhytnik.shop.dto.core.converter.impl.DtoSupport.getDtoClass;
import static com.zhytnik.shop.dto.core.converter.impl.DtoSupport.isSimple;

/**
 * @author Alexey Zhytnik
 * @since 23.06.2016
 */
class DynamicConverter {

    private static final MultiStringConverter stringConverter = new MultiStringConverter();

    public Map<String, Object> convert(Object[] values, DynamicType type) {
        final Map<String, Object> dtos = createMap(values.length);

        final List<DynamicField> fields = type.getFields();
        for (int i = 0, size = fields.size(); i < size; i++) {
            final DynamicField field = fields.get(i);
            final PrimitiveType t = field.getPrimitiveType();
            Object value;

            if (isSimple(t)) {
                value = values[i];
            } else {
                value = convertNotPrimitive(values[i], t);
            }
            dtos.put(field.getName(), value);
        }
        return dtos;
    }

    private Object convertNotPrimitive(Object value, PrimitiveType type) {
        final Class<?> fieldClass = getDtoClass(type);
        final Object result;
        if (fieldClass.isAssignableFrom(Date.class)) {
            result = (value != null) ? ((Date) value).getTime() : null;
        } else {
            result = (value != null) ? convertToDtoByClass(value, fieldClass) : null;
        }
        return result;
    }

    private Dto convertToDtoByClass(Object value, Class clazz) {
        Dto dto = null;
        if (clazz.equals(MultiStringDto.class)) {
            dto = stringConverter.convert((MultilanguageString) value);
        }
        return dto;
    }

    private static HashMap<String, Object> createMap(int count) {
        final int size = ((int) (count / 0.75d)) + 1;
        return new HashMap<>(size);
    }
}
