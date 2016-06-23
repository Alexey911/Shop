package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.dto.FieldDto;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;

import static com.zhytnik.shop.dto.core.converter.impl.DtoManager.mergeIdentity;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
class FieldDtoConverter implements IDtoConverter<DynamicField, FieldDto> {

    private IDtoConverter<MultilanguageString, MultiStringDto> stringConverter;

    @Override
    public DynamicField convert(FieldDto dto) {
        final DynamicField field = new DynamicField();
        mergeIdentity(field, dto);
        field.setName(dto.getName());
        field.setOrder(dto.getOrder());
        field.setRequired(dto.isRequired());
        field.setPrimitiveType(dto.getType());
        field.setDescription(stringConverter.convert(dto.getDescription()));
        return field;
    }

    public void setMultiLanguageConverter(IDtoConverter<MultilanguageString, MultiStringDto> converter) {
        this.stringConverter = converter;
    }
}
