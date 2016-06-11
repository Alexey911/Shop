package com.zhytnik.shop.controller.converter;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.dto.FieldDto;
import com.zhytnik.shop.dto.TypeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
@Component
public class TypeConverter implements Converter<DynamicType, TypeDto> {

    private Converter<DynamicField, FieldDto> fieldConverter = new FieldConverter();

    @Override
    public TypeDto convert(DynamicType type) {
        final TypeDto dto = new TypeDto();
        dto.setId(type.getId());
        dto.setName(type.getName());
        dto.setLastChange(type.getChangeDate());
        dto.setFields(convertFields(type.getFields()));
        return dto;
    }

    @Override
    public DynamicType convert(TypeDto dto) {
        final DynamicType type = new DynamicType();
        type.setId(dto.getId());
        type.setName(dto.getName());
        type.setChangeDate(dto.getLastChange());
        type.setFields(convertFields(type, dto));
        return type;
    }

    private List<FieldDto> convertFields(List<DynamicField> fields) {
        final List<FieldDto> dtos = new ArrayList<>(fields.size());
        for (DynamicField field : fields) dtos.add(fieldConverter.convert(field));
        return dtos;
    }

    private List<DynamicField> convertFields(DynamicType type, TypeDto dto) {
        final List<FieldDto> dtoFields = dto.getFields();
        final List<DynamicField> fields = new ArrayList<>(dtoFields.size());
        for (FieldDto fieldDto : dtoFields) {
            final DynamicField field = fieldConverter.convert(fieldDto);
            field.setType(type);
            fields.add(field);
        }
        return fields;
    }
}
