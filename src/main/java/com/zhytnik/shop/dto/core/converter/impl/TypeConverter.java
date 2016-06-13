package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.dto.FieldDto;
import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
@Component
class TypeConverter implements IEntityConverter<DynamicType, TypeDto> {

    private IEntityConverter<DynamicField, FieldDto> fieldConverter = new FieldConverter();

    @Override
    public TypeDto convert(DynamicType type) {
        final TypeDto dto = new TypeDto();
        dto.setId(type.getId());
        dto.setName(type.getName());
        dto.setLastChange(type.getChangeDate());
        dto.setFields(convertFields(type.getFields()));
        return dto;
    }

    private List<FieldDto> convertFields(List<DynamicField> fields) {
        final List<FieldDto> dtos = new ArrayList<>(fields.size());
        for (DynamicField field : fields) dtos.add(fieldConverter.convert(field));
        return dtos;
    }
}
