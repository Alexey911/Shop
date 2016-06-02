package com.zhytnik.shop.backend.access;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.exeception.InfrastructureException;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.zhytnik.shop.backend.tool.TypeUtil.getNativeClass;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicAccessor {

    private Map<String, Integer> mapping;

    private IDynamicEntity entity;

    private Set<Integer> changedFields = newHashSet();

    public DynamicAccessor(IDynamicEntity entity) {
        this.entity = entity;
        initializeMapping();
    }

    private void initializeMapping() {
        mapping = newHashMap();
        for (DynamicField column : entity.getDynamicType().getFields()) {
            mapping.put(column.getName(), column.getOrder());
        }
    }

    public void set(String field, Object value) {
        final Integer pos = mapping.get(field);
        if (pos == null) failOnUnknownAccessField(field);
        setValue(value, pos);
    }

    private void setValue(Object newValue, Integer pos) {
        final Object oldValue = entity.getDynamicFieldsValues()[pos];
        checkTypeCast(getFieldTypeByPosition(pos), newValue);
        if (hasChanges(newValue, oldValue)) changedFields.add(pos);
        entity.getDynamicFieldsValues()[pos] = newValue;
    }

    private void checkTypeCast(Class clazz, Object value) {
        boolean valid = clazz.equals(value.getClass());
        if (!valid) {
            throw new InfrastructureException(format("Can't cast value \"%s\" to \"%s\" class",
                    value, clazz));
        }
    }

    private Class getFieldTypeByPosition(Integer pos) {
        final PrimitiveType type = entity.getDynamicType().getFields().get(pos).getType();
        return getNativeClass(type);
    }

    private boolean hasChanges(Object newValue, Object oldValue) {
        return oldValue == newValue && oldValue != null && oldValue.hashCode() == newValue.hashCode();
    }

    public Object get(String field) {
        final Integer pos = mapping.get(field);
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
