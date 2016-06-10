package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.cache.TypeCache;
import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.backend.tool.DatabaseUtil;
import com.zhytnik.shop.backend.tool.TypeCreator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exeception.InfrastructureException;
import com.zhytnik.shop.exeception.NotFoundException;
import com.zhytnik.shop.exeception.NotUniqueException;
import com.zhytnik.shop.exeception.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class TypeService {

    private TypeCache cache;
    private TypeCreator typeCreator;
    private TypeRepository repository;

    @Transactional
    public void create(DynamicType type) {
        checkUniqueName(type);
        prepareFields(type);
        typeCreator.create(type);
        repository.save(type);
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
            field.setType(type);
        }
        if (positions.size() != fields.size()) {
            throw new ValidationException();
        }
    }

    @Transactional(readOnly = true)
    public boolean isUniqueName(String name) {
        return repository.findByName(name).isEmpty();
    }

    @Transactional
    public void remove(Long id) {
        String typeName;
        try {
            final DynamicType type = repository.findOne(id);
            if (type == null) throw new NotFoundException();

            typeName = type.getName();
            repository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(e);
        }
        try {
            DatabaseUtil.getInstance().dropTable(typeName);
        } catch (DataAccessException e) {
            throw new InfrastructureException(e);
        }
    }

    @Transactional
    public void update(DynamicType type) {
        checkUniqueNameChange(type);
        prepareFields(type);
        type.setChangeDate(new Date());
        repository.save(type);
    }

    private void checkUniqueNameChange(DynamicType type) {
        final String oldName = findById(type.getId()).getName();
        final String newName = type.getName();
        if (!oldName.equals(newName) && !isUniqueName(newName)) {
            throw new NotUniqueException();
        }
    }

    @Transactional(readOnly = true)
    public List<DynamicType> loadAll() {
        return newArrayList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public DynamicType findById(Long id) {
        return repository.findOne(id);
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
