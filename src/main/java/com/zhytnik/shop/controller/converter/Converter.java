package com.zhytnik.shop.controller.converter;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.dto.Dto;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
public interface Converter<T extends IDomainObject, TD extends Dto> {

    TD convert(T object);

    T convert(TD object);
}
