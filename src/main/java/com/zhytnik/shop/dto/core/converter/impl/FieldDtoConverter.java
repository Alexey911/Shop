package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.dto.FieldDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;

import static com.zhytnik.shop.dto.core.converter.impl.DtoUtil.mergeIdentity;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
class FieldDtoConverter implements IDtoConverter<DynamicField, FieldDto> {

    @Override
    public DynamicField convert(FieldDto dto) {
        final DynamicField field = new DynamicField();
        mergeIdentity(field, dto);
        field.setName(dto.getName());
        field.setOrder(dto.getOrder());
        field.setRequired(dto.isRequired());
        field.setPrimitiveType(dto.getType());
        return field;
    }
}
