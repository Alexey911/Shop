package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.dto.core.Identity;

/**
 * @author Alexey Zhytnik
 * @since 17.06.2016
 */
class DtoUtil {

    private DtoUtil() {
    }

    static void mergeIdentity(IDomainObject domainObject, Identity dto) {
        if (dto.getId() != null) {
            domainObject.setId(dto.getId());
        }
    }
}
