package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.TypeCreator;
import com.zhytnik.shop.backend.repository.ProductRepository;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exeception.NotUniqueException;
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

    @Autowired
    private ProductRepository repository;

    public void create(DynamicType type) {
        checkUniqueName(type);
        typeCreator.create(type);
        repository.save(type);
    }

    private void checkUniqueName(DynamicType type) {
        final String name = type.getName();
        if (!repository.findByName(name).isEmpty()) {
            throw new NotUniqueException();
        }
    }
}
