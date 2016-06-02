package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.TypeCreator;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class TypeService {

    @Autowired
    private TypeCreator typeCreator;

    public void create(DynamicType type) {
        typeCreator.create(type);
    }
}
