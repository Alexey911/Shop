package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.backend.tool.DatabaseUtil;
import com.zhytnik.shop.backend.tool.TypeCreator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exception.InfrastructureException;
import com.zhytnik.shop.exception.NotFoundException;
import com.zhytnik.shop.exception.NotUniqueException;
import com.zhytnik.shop.exception.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class TypeService {

    private TypeCreator typeCreator;
    private TypeRepository repository;

    @Transactional(readOnly = true)
    public DynamicType findById(Long id) {
        final DynamicType type = repository.findOne(id);
        if (type == null) throw new NotFoundException("not.found.by.id", id);
        return type;
    }

    @Transactional(readOnly = true)
    public List<DynamicType> loadAll() {
        return newArrayList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public boolean isUniqueName(String name) {
        return repository.findByName(name).isEmpty();
    }

    @Transactional
    public void create(DynamicType type) {
        checkUniqueName(type);
        prepareFields(type);
        typeCreator.create(type);
        repository.save(type);
    }

    @Transactional
    public void update(DynamicType type) {
        prepareChangeName(type);
        prepareFields(type);
        try {
            repository.save(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void remove(Long id) {
        String typeName;
        try {
            typeName = findById(id).getName();
            repository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(e);
        }
        dropTypeTable(typeName);
    }

    private void checkUniqueName(DynamicType type) {
        if (!isUniqueName(type.getName())) {
            throw new NotUniqueException();
        }
    }

    private void prepareFields(DynamicType type) {
        final List<DynamicField> fields = type.getFields();
        final Set<Integer> positions = newHashSet();
        for (DynamicField field : fields) {
            if (field.getOrder() == null)
                throw new ValidationException("Empty field order");
            positions.add(field.getOrder());
        }
        if (positions.size() != fields.size()) {
            throw new ValidationException("wrong.field.order");
        }
    }

    private void dropTypeTable(String typeName) {
        try {
            DatabaseUtil.getInstance().dropTable(typeName);
        } catch (DataAccessException e) {
            throw new InfrastructureException(e);
        }
    }

    private void prepareChangeName(DynamicType type) {
        final String oldName = findById(type.getId()).getName();
        final String newName = type.getName();
        if (!oldName.equals(newName) && !isUniqueName(newName)) {
            throw new NotUniqueException();
        }
        dropTypeTable(oldName);
        typeCreator.create(type);
    }

    public void setTypeCreator(TypeCreator typeCreator) {
        this.typeCreator = typeCreator;
    }

    public void setRepository(TypeRepository repository) {
        this.repository = repository;
    }
}
