package com.zhytnik.shop.backend.dao.query;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.hibernate.type.StringRepresentableType;

import javax.persistence.Query;
import java.util.List;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.getDynamicValues;
import static com.zhytnik.shop.backend.access.DynamicAccessUtil.setDynamicValues;
import static com.zhytnik.shop.backend.tool.TypeUtil.getTypeConverter;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
class QueryUtil {

    private QueryUtil() {
    }

    static void fillQuery(Query query, IDynamicEntity entity) {
        final Object[] values = getDynamicValues(entity);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i + 1, getInsertValue(values[i]));
        }
    }

    private static Object getInsertValue(Object value) {
        if (value instanceof IDomainObject) {
            return ((IDomainObject) value).getId();
        }
        return value;
    }

    static void fillDynamic(Object rowValues, IDynamicEntity entity) {
        if (!isArray(rowValues)) {
            fillDynamic(new Object[]{rowValues}, entity);
            return;
        }
        final List<DynamicField> fields = entity.getDynamicType().getFields();
        final int size = fields.size();
        final Object[] values = (Object[]) rowValues;

        for (int i = 0; i < size; i++) {
            final StringRepresentableType nativeType = getTypeConverter(fields.get(i).getPrimitiveType());
            values[i] = nativeType.fromStringValue(values[i].toString());
        }
        setDynamicValues(entity, values);
    }

    private static boolean isArray(Object obj) {
        return obj instanceof Object[];
    }
}
