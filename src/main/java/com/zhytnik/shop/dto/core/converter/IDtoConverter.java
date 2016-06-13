package com.zhytnik.shop.dto.core.converter;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.dto.core.Dto;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
public interface IDtoConverter<T extends IDomainObject, TD extends Dto> {

    T convert(TD object);
}
