package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.backend.tool.DatabaseUtil;
import com.zhytnik.shop.backend.tool.TypeCreator;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exception.NotFoundException;
import com.zhytnik.shop.exception.NotUniqueException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.assertion.CommonAssert.*;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class TypeService {

    private TypeCreator typeCreator;
    private TypeRepository repository;

    @Transactional(readOnly = true)
    public List<DynamicType> loadAll() {
        return newArrayList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public boolean isUniqueName(String name) {
        notEmptyName(name);
        return repository.findByName(name.trim().toLowerCase()).isEmpty();
    }

    @Transactional(readOnly = true)
    public DynamicType findById(Long id) {
        notEmptyId(id);
        final DynamicType type = repository.findOne(id);
        shouldBeFoundById(type, id);
        return type;
    }

    @Transactional
    public void create(DynamicType type) {
        checkUniqueName(type);
        typeCreator.create(type);
        repository.save(type);
    }

    private void checkUniqueName(DynamicType type) {
        if (!isUniqueName(type.getName())) {
            throw new NotUniqueException();
        }
    }

    @Transactional
    public void update(DynamicType type) {
        checkRightRename(type);
        clearType(type.getName());
        typeCreator.create(type);
        repository.save(type);
    }

    private void checkRightRename(DynamicType type) {
        final String oldName = findById(type.getId()).getName();
        final String newName = type.getName();
        if (!oldName.equals(newName) && !isUniqueName(newName)) {
            throw new NotUniqueException();
        }
    }

    @Transactional
    public void remove(Long id) {
        String name;
        try {
            name = findById(id).getName();
            repository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(e);
        }
        clearType(name);
    }

    private void clearType(String name) {
        //TODO: remove all type products
        DatabaseUtil.getInstance().dropTable(name);
    }

    public void setTypeCreator(TypeCreator typeCreator) {
        this.typeCreator = typeCreator;
    }

    public void setRepository(TypeRepository repository) {
        this.repository = repository;
    }
}
