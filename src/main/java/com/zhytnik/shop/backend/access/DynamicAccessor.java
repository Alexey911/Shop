package com.zhytnik.shop.backend.access;

import com.zhytnik.shop.domain.dynamic.ColumnType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.exeception.InfrastructureException;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicAccessor {

    private Map<String, Integer> mapping;

    private IDynamicEntity entity;

    private Set<Integer> changedFields;

    public DynamicAccessor(IDynamicEntity entity) {
        this.entity = entity;
        changedFields = newHashSet();
        initializeMapping();
    }

    private void initializeMapping() {
        mapping = newHashMap();
        for (ColumnType column : entity.getDynamicType().getColumns()) {
            mapping.put(column.getName(), column.getOrder());
        }
    }

    public void set(String field, Object value) {
        Integer pos = mapping.get(field);
        if (pos == null) failOnUnknownAccessField(field);
        setValue(value, pos);
    }

    private void setValue(Object newValue, Integer pos) {
        final Object oldValue = entity.getDynamicFieldsValues()[pos];
        if (hasChanges(newValue, oldValue)) changedFields.add(pos);
        entity.getDynamicFieldsValues()[pos] = newValue;
    }

    private boolean hasChanges(Object newValue, Object oldValue) {
        return oldValue == newValue && oldValue != null && oldValue.hashCode() == newValue.hashCode();
    }

    public Object get(String field) {
        Integer pos = mapping.get(field);
        if (pos == null) failOnUnknownAccessField(field);
        return entity.getDynamicFieldsValues()[pos];
    }

    private void failOnUnknownAccessField(String field) {
        throw new InfrastructureException(format("Unknown field \"%s\"", field));
    }

    public Set<Integer> getChangesFieldIds() {
        return changedFields;
    }

    public void resetChanges() {
        changedFields.clear();
    }
}
