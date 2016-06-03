package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.backend.dao.search.Filter;
import com.zhytnik.shop.backend.dao.search.SearchBuilder;
import com.zhytnik.shop.backend.tool.DatabaseUtil;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.exeception.NotFoundException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.sql.Delete;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Select;
import org.hibernate.sql.Update;
import org.hibernate.transform.Transformers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
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
        fillQuery(entity, query);
        query.executeUpdate();
    }

    static void updateDynamic(Session session, IDynamicEntity entity) {
        if (!hasChanges(entity)) return;
        final Update update = prepareUpdate(entity);
        final SQLQuery query = session.createSQLQuery(update.toStatementString());
        fillQueryByFields(entity, query, entity.getDynamicAccessor().getChangesFieldIds());
        query.executeUpdate();
    }

    private static boolean hasChanges(IDynamicEntity entity) {
        return !entity.getDynamicAccessor().getChangesFieldIds().isEmpty();
    }

    private static void fillQuery(IDynamicEntity entity, SQLQuery query) {
        final Object[] values = getDynamicValues(entity);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
    }

    private static void fillQueryByFields(IDynamicEntity entity, SQLQuery query, Set<Integer> fields) {
        final Object[] values = getDynamicValues(entity);
        int pos = 0;
        for (int field : fields) {
            query.setParameter(pos++, values[field]);
        }
    }

    @SuppressWarnings("unchecked")
    static void loadDynamic(Session session, IDynamicEntity entity) {
        final Select select = prepareLoad(entity);
        final SQLQuery query = session.createSQLQuery(select.toStatementString());
        setResultTransform(query, entity.getDynamicType());

        final List<List<?>> entities = query.list();
        if (entities.isEmpty()) throw new NotFoundException();

        setDynamicValues(entity, extractDynamicData(entities.get(0), 0));
    }

    @SuppressWarnings("unchecked")
    static <T extends IDynamicEntity> List<T> findByQuery(Class<T> clazz, Session session,
                                                          DynamicType type, Filter filter) {
        final Query query = SearchBuilder.build(session, filter, type);
        final List<List<?>> dynamicEntitiesValues = query.list();

        final List<T> entities = newArrayList();
        for (List<?> dynamicEntityValues : dynamicEntitiesValues) {
            final Long id = (Long) dynamicEntityValues.get(0);
            final T entity = session.load(clazz, id);

            final Object[] values = extractDynamicData(dynamicEntityValues, 1);
            setDynamicValues(entity, values);

            entities.add(entity);
        }
        return entities;
    }

    public static void setResultTransform(SQLQuery query, DynamicType type) {
        for (DynamicField column : type.getFields()) {
            query.addScalar(column.getName(), getTypeConverter(column.getType()));
        }
        query.setResultTransformer(Transformers.TO_LIST);
    }

    private static Object[] extractDynamicData(List<?> data, int start) {
        final int count = data.size();
        final Object[] values = new Object[count - start];
        for (int i = start; i < count; i++) {
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
        final Update update = new Update(DatabaseUtil.getDialect());
        update.setTableName(entity.getDynamicType().getName()).
                addWhereColumn(DYNAMIC_ID_FIELD, "=" + Long.toString(entity.getId()));

        final List<DynamicField> columns = getFields(entity);
        final Set<Integer> changes = entity.getDynamicAccessor().getChangesFieldIds();
        for (int change : changes) {
            update.addColumn(columns.get(change).getName());
        }
        return update;
    }

    private static Insert prepareInsert(IDynamicEntity entity) {
        final Insert insert = new Insert(DatabaseUtil.getDialect());
        insert.setTableName(entity.getDynamicType().getName());

        final List<DynamicField> columns = getFields(entity);
        for (int i = 0; i < columns.size(); i++) {
            insert.addColumn(columns.get(i).getName());
        }
        insert.addColumn(DYNAMIC_ID_FIELD, Long.toString(entity.getId()));
        return insert;
    }

    private static Select prepareLoad(IDynamicEntity entity) {
        return getDefaultSelect(entity.getDynamicType(), false).
                setWhereClause(DYNAMIC_ID_FIELD + " = " + entity.getId());
    }

    public static Select getDefaultSelect(DynamicType type, boolean withId) {
        final Select select = new Select(DatabaseUtil.getDialect());
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

    private static List<DynamicField> getFields(IDynamicEntity entity) {
        return entity.getDynamicType().getFields();
    }

    @SuppressWarnings("deprecation")
    public static void setDynamicValues(IDynamicEntity entity, Object[] values) {
        entity.setDynamicFieldsValues(values);
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
}
