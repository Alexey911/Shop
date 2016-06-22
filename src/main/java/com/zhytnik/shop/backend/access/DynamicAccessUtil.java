package com.zhytnik.shop.backend.access;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicAccessUtil {

    private DynamicAccessUtil() {
    }

    @SuppressWarnings("deprecation")
    public static void setDynamicValues(IDynamicEntity entity, Object[] values) {
        entity.setDynamicFieldsValues(values);
    }

    @SuppressWarnings("deprecation")
    public static void initialDynamicValues(IDynamicEntity entity, int size) {
        entity.setDynamicFieldsValues(new Object[size]);
    }

    @SuppressWarnings("deprecation")
    public static void setDynamicValue(IDynamicEntity entity, int pos, Object value) {
        entity.getDynamicFieldsValues()[pos] = value;
    }

    @SuppressWarnings("deprecation")
    public static Object[] getDynamicValues(IDynamicEntity entity) {
        return entity.getDynamicFieldsValues();
    }

    @SuppressWarnings("deprecation")
    public static Object getDynamicValue(IDynamicEntity entity, int pos) {
        return entity.getDynamicFieldsValues()[pos];
    }

    public static List<DynamicField> getFields(IDynamicEntity entity) {
        return entity.getDynamicType().getFields();
    }

    public static boolean hasChanges(IDynamicEntity entity) {
        return !entity.getDynamicAccessor().getChangesFieldIds().isEmpty();
    }

    public static Set<Integer> getChangesFields(IDynamicEntity entity) {
        return entity.getDynamicAccessor().getChangesFieldIds();
    }
}
