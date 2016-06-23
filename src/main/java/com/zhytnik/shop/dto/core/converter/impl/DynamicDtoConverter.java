package com.zhytnik.shop.dto.core.converter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.exception.InfrastructureException;

import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.google.common.collect.Maps.newHashMap;
import static com.zhytnik.shop.dto.core.converter.impl.DtoSupport.getDtoClass;

/**
 * @author Alexey Zhytnik
 * @since 23.06.2016
 */
class DynamicDtoConverter {

    private static MultiStringDtoConverter stringDtoConverter = new MultiStringDtoConverter();

    private ObjectMapper mapper = new ObjectMapper();

    public Object[] convert(Map<String, Object> dynamic, DynamicType type) {
        final Object[] values = new Object[type.getFields().size()];
        convert(dynamic, values, getFieldDescription(type.getFields()));
        return values;
    }

    //TODO: in future use cached descriptions
    @SuppressWarnings("unchecked")
    private Map<String, Entry<Integer, Class>> getFieldDescription(List<DynamicField> fields) {
        final Map<String, Entry<Integer, Class>> description = newHashMap();
        for (DynamicField field : fields) {
            final Integer order = field.getOrder();
            final Class clazz = getDtoClass(field.getPrimitiveType());
            description.put(field.getName(), new AbstractMap.SimpleEntry(order, clazz));
        }
        return description;
    }

    private Object[] convert(Map<String, Object> dynamic, Object[] values,
                             Map<String, Entry<Integer, Class>> definition) {
        for (Entry<String, Object> field : dynamic.entrySet()) {
            final Entry<Integer, Class> desc = definition.get(field.getKey());
            values[desc.getKey()] = convert(field.getValue(), desc.getValue());
        }
        return values;
    }

    private Object convert(Object rowField, Class type) {
        if (DtoSupport.isPrimitive(type)) return rowField;
        if (type.equals(Date.class)) return new Date(Long.valueOf(rowField.toString()));
        return convertComplexDto(rowField, type);
    }

    @SuppressWarnings("unchecked")
    private Object convertComplexDto(Object rowField, Class type) {
        Dto field;
        try {
            field = (Dto) mapper.convertValue(rowField, type);
        } catch (IllegalArgumentException e) {
            throw new InfrastructureException(e);
        }
        return convertDto(field, type);
    }

    private static Object convertDto(Dto dto, Class<? extends Dto> type) {
        Object value = null;
        if (type.equals(MultiStringDto.class)) {
            value = stringDtoConverter.convert((MultiStringDto) dto);
        }
        return value;
    }
}
