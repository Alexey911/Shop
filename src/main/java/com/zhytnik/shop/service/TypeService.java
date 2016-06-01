package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.TypeCreator;
import com.zhytnik.shop.domain.dynamic.DynamicType;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class TypeService {

    private TypeCreator creator;

    public void create(DynamicType type) {
        creator.create(type);
    }
}
