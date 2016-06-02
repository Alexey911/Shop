package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.exeception.NotFoundException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.sql.Delete;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Select;
import org.hibernate.sql.Update;
import org.hibernate.transform.Transformers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.zhytnik.shop.backend.tool.TypeUtil.getTypeConverter;
import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicUtil {

    private DynamicUtil() {
    }

    static void persistDynamic(Session session, IDynamicEntity entity) {
        final Insert insert = prepareInsert(entity);
        final SQLQuery query = session.createSQLQuery(insert.toStatementString());
        query.executeUpdate();
    }

    static void updateDynamic(Session session, IDynamicEntity entity) {
        if (!hasChanges(entity)) return;
        final Update update = prepareUpdate(entity);
        final SQLQuery query = session.createSQLQuery(update.toStatementString());
        query.executeUpdate();
    }

    private static boolean hasChanges(IDynamicEntity entity) {
        return !entity.getDynamicAccessor().getChangesFieldIds().isEmpty();
    }

    @SuppressWarnings("unchecked")
    static void loadDynamic(Session session, IDynamicEntity entity) {
        final Select select = prepareLoad(entity);
        final SQLQuery query = session.createSQLQuery(select.toStatementString());
        setResultTransform(query, entity);
        query.executeUpdate();

        final List<List<?>> entities = query.list();
        if (entities.isEmpty()) throw new NotFoundException();

        entity.setDynamicFieldsValues(extractDynamicData(entities.get(0)));
    }

    private static void setResultTransform(SQLQuery query, IDynamicEntity entity) {
        for (DynamicField column : getColumns(entity)) {
            query.addScalar(column.getName(), getTypeConverter(column.getType()));
        }
        query.setResultTransformer(Transformers.TO_LIST);
    }

    private static Object[] extractDynamicData(List<?> data) {
        final int size = data.size();
        final Object[] values = new Object[size];
        for (int i = 0; i < size; i++) {
            values[i] = data.get(i);
        }
        return values;
    }

    static void deleteDynamic(Session session, IDynamicEntity entity) {
        final Delete delete = prepareDelete(entity);
        final SQLQuery query = session.createSQLQuery(delete.toStatementString());
        query.executeUpdate();
    }

    private static Delete prepareDelete(IDynamicEntity entity) {
        final Delete delete = new Delete();
        delete.setTableName(entity.getDynamicType().getName()).
                addPrimaryKeyColumn(DYNAMIC_ID_FIELD, Long.toString(entity.getId()));
        return delete;
    }

    private static Update prepareUpdate(IDynamicEntity entity) {
        final Update update = new Update(new Oracle10gDialect());
        update.setTableName(entity.getDynamicType().getName()).
                addWhereColumn(DYNAMIC_ID_FIELD, Long.toString(entity.getId()));

        final List<DynamicField> columns = getColumns(entity);
        final Object[] values = entity.getDynamicFieldsValues();
        final Set<Integer> changes = entity.getDynamicAccessor().getChangesFieldIds();

        for (int change : changes) {
            update.addColumn(columns.get(change).getName(), values[change].toString());
        }
        return update;
    }

    private static Insert prepareInsert(IDynamicEntity entity) {
        final Insert insert = new Insert(new Oracle10gDialect());
        insert.setTableName(entity.getDynamicType().getName());

        final List<DynamicField> columns = getColumns(entity);
        final Object[] values = entity.getDynamicFieldsValues();

        for (int i = 0; i < columns.size(); i++) {
            insert.addColumn(columns.get(i).getName(), values[i].toString());
        }
        insert.addColumn(DYNAMIC_ID_FIELD, Long.toString(entity.getId()));
        return insert;
    }

    private static Select prepareLoad(IDynamicEntity entity) {
        final Select select = new Select(new Oracle10gDialect());
        select.setFromClause(entity.getDynamicType().getName()).
                setSelectClause(initialSelect(entity)).
                setWhereClause(DYNAMIC_ID_FIELD + " = " + entity.getId());
        return select;
    }

    private static String initialSelect(IDynamicEntity entity) {
        final List<DynamicField> columns = getColumns(entity);
        final StringBuilder select = new StringBuilder(columns.size() * 15 + 10);
        final Iterator<DynamicField> iterator = columns.iterator();

        while (iterator.hasNext()) {
            select.append(iterator.next().getName());
            if (iterator.hasNext()) {
                select.append(", ");
            }
        }
        return select.toString();
    }

    private static List<DynamicField> getColumns(IDynamicEntity entity) {
        return entity.getDynamicType().getFields();
    }
}
