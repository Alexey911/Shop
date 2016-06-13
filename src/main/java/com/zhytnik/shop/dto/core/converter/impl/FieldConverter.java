package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.dto.FieldDto;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
class FieldConverter implements IEntityConverter<DynamicField, FieldDto> {

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
}
