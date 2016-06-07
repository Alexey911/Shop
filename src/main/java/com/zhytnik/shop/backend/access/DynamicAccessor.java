package com.zhytnik.shop.backend.access;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.exeception.InfrastructureException;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.zhytnik.shop.backend.access.DynamicAccessUtil.getDynamicValue;
import static com.zhytnik.shop.backend.tool.TypeUtil.getNativeClass;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicAccessor {

    private IDynamicEntity entity;

    private Set<Integer> changedFields = newHashSet();
    private Map<String, Integer> mapping = newHashMap();

    public DynamicAccessor(IDynamicEntity entity) {
        this.entity = entity;
        initializeMapping();
    }

    //TODO: using cached maps
    private void initializeMapping() {
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
        final Object oldValue = getDynamicValue(entity, pos);
        checkTypeCast(getFieldTypeByPosition(pos), newValue);
        if (hasChanges(newValue, oldValue)) changedFields.add(pos);
        DynamicAccessUtil.setDynamicValue(entity, pos, newValue);
    }

    private void checkTypeCast(Class clazz, Object value) {
        if (value == null) return;
        boolean valid = clazz.equals(value.getClass());
        if (!valid) {
            throw new InfrastructureException(format("Can't cast value \"%s\" to \"%s\" class",
                    value, clazz));
        }
    }

    private Class getFieldTypeByPosition(Integer pos) {
        final PrimitiveType type = entity.getDynamicType().getFields().get(pos).getPrimitiveType();
        return getNativeClass(type);
    }

    private boolean hasChanges(Object newValue, Object oldValue) {
        return oldValue != newValue || oldValue != null && oldValue.hashCode() != newValue.hashCode();
    }

    public Object get(String field) {
        final Integer pos = mapping.get(field);
        if (pos == null) failOnUnknownAccessField(field);
        return getDynamicValue(entity, pos);
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
