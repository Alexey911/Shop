package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.Identity;
import com.zhytnik.shop.exception.InfrastructureException;

import java.util.Date;

import static com.zhytnik.shop.domain.dynamic.PrimitiveType.*;

/**
 * @author Alexey Zhytnik
 * @since 17.06.2016
 */
class DtoSupport {

    private DtoSupport() {
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
        throw new InfrastructureException();
    }

    static boolean isPrimitive(PrimitiveType type) {
        return type.equals(BOOLEAN) || type.equals(DOUBLE) ||
                type.equals(LONG) || type.equals(TEXT);
    }

    static boolean isPrimitive(Class<?> clazz) {
        return clazz.equals(Boolean.class) || clazz.equals(Double.class) ||
                clazz.equals(Long.class) || clazz.equals(String.class);
    }
}
