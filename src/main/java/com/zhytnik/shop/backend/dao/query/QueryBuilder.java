package com.zhytnik.shop.backend.dao.query;

import com.zhytnik.shop.backend.tool.DatabaseUtil;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.hibernate.sql.Delete;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Select;
import org.hibernate.sql.Update;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
class QueryBuilder {

    private QueryBuilder() {
    }

    static Update createUpdate(DynamicType type, Set<Integer> fields, Long id) {
        final Update update = new Update(DatabaseUtil.getInstance().getDialect());
        update.setTableName(type.getName()).
                addWhereColumn(DYNAMIC_ID_FIELD, "=" + Long.toString(id));

        final List<DynamicField> columns = type.getFields();
        for (int field : fields) {
            update.addColumn(columns.get(field).getName());
        }
        return update;
    }

    static Insert createInsert(DynamicType type, Long id) {
        final Insert insert = new Insert(DatabaseUtil.getInstance().getDialect());
        insert.setTableName(type.getName());

        final List<DynamicField> columns = type.getFields();
        for (int i = 0; i < columns.size(); i++) {
            insert.addColumn(columns.get(i).getName());
        }
        insert.addColumn(DYNAMIC_ID_FIELD, Long.toString(id));
        return insert;
    }

    static Delete createDelete(DynamicType type, Long id) {
        final Delete delete = new Delete();
        delete.setTableName(type.getName()).
                addPrimaryKeyColumn(DYNAMIC_ID_FIELD, Long.toString(id));
        return delete;
    }

    //TODO: use join
    static Select createSelect(IDynamicEntity entity) {
        return getDefaultSelect(entity.getDynamicType(), false).
                setWhereClause(DYNAMIC_ID_FIELD + " = " + entity.getId());
    }

    static Select getDefaultSelect(DynamicType type, boolean withId) {
        final Select select = new Select(DatabaseUtil.getInstance().getDialect());
        select.setFromClause(type.getName());
        select.setSelectClause(initialSelect(type, withId));
        return select;
    }

    private static String initialSelect(DynamicType type, boolean withId) {
        final List<DynamicField> columns = type.getFields();
        final StringBuilder select = new StringBuilder(columns.size() * 15 + 10);
        final Iterator<DynamicField> iterator = columns.iterator();

        if (withId) select.append(DYNAMIC_ID_FIELD).append(", ");

        while (iterator.hasNext()) {
            select.append(iterator.next().getName());
            if (iterator.hasNext()) {
                select.append(", ");
            }
        }
        return select.toString();
    }
}
