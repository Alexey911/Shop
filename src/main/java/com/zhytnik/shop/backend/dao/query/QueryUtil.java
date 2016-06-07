package com.zhytnik.shop.backend.dao.query;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Set;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.getDynamicValues;
import static com.zhytnik.shop.backend.tool.TypeUtil.getTypeConverter;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
class QueryUtil {

    private QueryUtil() {
    }

    static void fillQuery(SQLQuery query, IDynamicEntity entity) {
        final Object[] values = getDynamicValues(entity);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
    }

    static void fillQueryByFields(SQLQuery query, IDynamicEntity entity, Set<Integer> fields) {
        final Object[] values = getDynamicValues(entity);
        int pos = 0;
        for (int field : fields) {
            query.setParameter(pos++, values[field]);
        }
    }

    static void setResultTransformByType(SQLQuery query, DynamicType type) {
        for (DynamicField column : type.getFields()) {
            query.addScalar(column.getName(), getTypeConverter(column.getPrimitiveType()));
        }
        query.setResultTransformer(Transformers.TO_LIST);
    }

    static Object[] transformQueryData(List<?> data, int start) {
        final int count = data.size();
        final Object[] values = new Object[count - start];
        for (int i = start; i < count; i++) {
            values[i] = data.get(i);
        }
        return values;
    }
}
