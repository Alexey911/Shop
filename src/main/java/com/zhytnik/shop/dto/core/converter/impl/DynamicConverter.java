package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.Dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhytnik.shop.dto.core.converter.impl.DtoSupport.getDtoClass;
import static com.zhytnik.shop.dto.core.converter.impl.DtoSupport.isPrimitive;

/**
 * @author Alexey Zhytnik
 * @since 23.06.2016
 */
public class DynamicConverter {

    private MultiStringConverter stringConverter = new MultiStringConverter();

    public Map<String, Object> convert(Object[] values, DynamicType type) {
        final Map<String, Object> dtos = new HashMap<>(values.length);

        final List<DynamicField> fields = type.getFields();
        final int size = fields.size();
        for (int i = 0; i < size; i++) {
            final DynamicField field = fields.get(i);
            final String fieldName = field.getName();

            if (isPrimitive(field.getPrimitiveType())) {
                dtos.put(fieldName, values[i]);
                continue;
            }
            final Class<?> fieldClass = getDtoClass(field.getPrimitiveType());
            Object value;
            if (fieldClass.isAssignableFrom(Date.class)) {
                value = ((Date) values[i]).getTime();
            } else {
                value = convert(values[i], fieldClass);
            }
            dtos.put(field.getName(), value);
        }
        return dtos;
    }


    private Dto convert(Object value, Class clazz) {
        if (value == null) return null;

        Dto dto = null;
        if (clazz.equals(MultiStringDto.class)) {
            dto = stringConverter.convert((MultilanguageString) value);
        }
        return dto;
    }
}
