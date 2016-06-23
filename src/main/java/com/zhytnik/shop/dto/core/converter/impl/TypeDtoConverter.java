package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.dto.FieldDto;
import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

import static com.zhytnik.shop.dto.core.converter.impl.DtoManager.mergeIdentity;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
class TypeDtoConverter implements IDtoConverter<DynamicType, TypeDto> {

    private IDtoConverter<DynamicField, FieldDto> fieldConverter;

    @Override
    public DynamicType convert(TypeDto dto) {
        final DynamicType type = new DynamicType();
        mergeIdentity(type, dto);
        type.setName(dto.getName());
        type.setChangeDate(dto.getLastChange());
        type.setFields(convertFields(type, dto));
        return type;
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

    @Required
    public void setFieldConverter(IDtoConverter<DynamicField, FieldDto> fieldConverter) {
        this.fieldConverter = fieldConverter;
    }
}
