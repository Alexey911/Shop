package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.dto.core.Identity;

import java.util.Date;

/**
 * @author Alexey Zhytnik
 * @since 17.06.2016
 */
class DtoManager {

    private static MultiStringDtoConverter stringDtoConverter = new MultiStringDtoConverter();

    private DtoManager() {
    }

    static void mergeIdentity(IDomainObject domainObject, Identity dto) {
        if (dto.getId() != null) {
            domainObject.setId(dto.getId());
        }
    }

    static Class<?> getDtoClass(PrimitiveType type) {
        switch (type) {
            case STRING:
                return MultiStringDto.class;
            case BOOLEAN:
                return Boolean.class;
            case DOUBLE:
                return Double.class;
            case LONG:
                return Long.class;
            case TEXT:
                return String.class;
            case DATE:
                return Date.class;
        }
        throw new RuntimeException();
    }

    static boolean isPrimitive(Class<?> clazz) {
        return clazz.equals(Boolean.class) || clazz.equals(Double.class) ||
                clazz.equals(Long.class) || clazz.equals(String.class);
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
