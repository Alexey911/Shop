package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.cache.TypeCache;
import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.backend.tool.TypeCreator;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exeception.NotUniqueException;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class TypeService {

    private TypeCache cache;
    private TypeCreator typeCreator;
    private TypeRepository repository;

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

    public DynamicType findById(Long id) {
        return cache.get(id);
    }

    public void setTypeCreator(TypeCreator typeCreator) {
        this.typeCreator = typeCreator;
    }

    public void setRepository(TypeRepository repository) {
        this.repository = repository;
    }

    public void setCache(TypeCache cache) {
        this.cache = cache;
    }
}
