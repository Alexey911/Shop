package com.zhytnik.shop.controller.converter;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.dto.FieldDto;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
class FieldConverter implements Converter<DynamicField, FieldDto> {

    @Override
    public FieldDto convert(DynamicField field) {
        final FieldDto dto = new FieldDto();
        dto.setId(field.getId());
        dto.setName(field.getName());
        dto.setOrder(field.getOrder());
        dto.setRequired(field.isRequired());
        dto.setPrimitiveType(field.getPrimitiveType());
        return null;
    }

    @Override
    public DynamicField convert(FieldDto dto) {
        final DynamicField field = new DynamicField();
        field.setId(dto.getId());
        field.setName(dto.getName());
        field.setOrder(dto.getOrder());
        field.setRequired(dto.isRequired());
        field.setPrimitiveType(dto.getPrimitiveType());
        return field;
    }
}
