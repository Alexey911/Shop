package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.dto.core.Identity;

/**
 * @author Alexey Zhytnik
 * @since 17.06.2016
 */
class DtoUtil {

    private static MultiStringDtoConverter stringDtoConverter = new MultiStringDtoConverter();

    private DtoUtil() {
    }

    static void mergeIdentity(IDomainObject domainObject, Identity dto) {
        if (dto.getId() != null) {
            domainObject.setId(dto.getId());
        }
    }

    static Class<? extends Dto> getDtoClass(PrimitiveType type) {
        switch (type) {
            case STRING:
                return MultiStringDto.class;
        }
        throw new RuntimeException();
    }

    public static Object convertDto(Dto dto, Class<? extends Dto> type) {
        Object value = null;
        if (type.equals(MultiStringDto.class)) {
            value = stringDtoConverter.convert((MultiStringDto) dto);
        }
        return value;
    }

    static Class<? extends Dto> getDto() {
        return MultiStringDto.class;
    }
}
