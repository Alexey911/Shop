package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.backend.tool.TypeUtil;
import com.zhytnik.shop.domain.dynamic.ColumnType;
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

import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicUtil {

    private DynamicUtil() {
    }

    public static void persistDynamic(Session session, IDynamicEntity entity) {
        preparePersistDynamic(entity);
        final Insert insert = prepareInsert(entity);
        final SQLQuery query = session.createSQLQuery(insert.toStatementString());
        query.executeUpdate();
    }

    private static void preparePersistDynamic(IDynamicEntity entity) {
        entity.getDynamicAccessor().set(DYNAMIC_ID_FIELD, entity.getId());
    }

    public static void updateDynamic(Session session, IDynamicEntity entity) {
        if (entity.getDynamicAccessor().getChangesFieldIds().isEmpty()) return;
        final Update update = prepareUpdate(entity);
        final SQLQuery query = session.createSQLQuery(update.toStatementString());
        query.executeUpdate();
    }

    public static void loadDynamic(Session session, IDynamicEntity entity) {
        final Select select = prepareLoad(entity);
        final SQLQuery query = session.createSQLQuery(select.toStatementString());
        for (ColumnType column : getColumns(entity)) {
            query.addScalar(column.getName(), TypeUtil.getTypeConverter(column.getType()));
        }
        query.executeUpdate();
        query.setResultTransformer(Transformers.TO_LIST);

        final List<List<?>> entities = query.list();
        if (entities.isEmpty()) throw new NotFoundException();

        final List<?> fieldsValues = entities.get(0);
        final Object[] values = new Object[fieldsValues.size()];
        for (int i = 0; i < fieldsValues.size(); i++) {
            values[i] = fieldsValues.get(i);
        }

        entity.setDynamicFieldsValues(values);
    }

    public static void deleteDynamic(Session session, IDynamicEntity entity) {
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

        final List<ColumnType> columns = getColumns(entity);
        final Object[] fields = entity.getDynamicFieldsValues();
        final Set<Integer> changes = entity.getDynamicAccessor().getChangesFieldIds();

        for (int i = 0; i < columns.size(); i++) {
            if (changes.contains(i)) {
                update.addColumn(columns.get(i).getName(), fields[i].toString());
            }
        }
        return update;
    }

    private static Insert prepareInsert(IDynamicEntity entity) {
        final Insert insert = new Insert(new Oracle10gDialect());
        insert.setTableName(entity.getDynamicType().getName());

        final List<ColumnType> columns = getColumns(entity);
        final Object[] fields = entity.getDynamicFieldsValues();

        for (int i = 0; i < columns.size(); i++) {
            insert.addColumn(columns.get(i).getName(), fields[i].toString());
        }
        return insert;
    }

    private static Select prepareLoad(IDynamicEntity entity) {
        final Select select = new Select(new Oracle10gDialect());
        select.setFromClause(entity.getDynamicType().getName()).
                setSelectClause(initialSelect(entity)).
                setWhereClause("ID = " + entity.getId());
        return select;
    }

    private static String initialSelect(IDynamicEntity entity) {
        final List<ColumnType> columns = getColumns(entity);
        final StringBuilder select = new StringBuilder(columns.size() * 15 + 10);
        final Iterator<ColumnType> iterator = columns.iterator();

        while (iterator.hasNext()) {
            select.append(iterator.next().getName());
            if (iterator.hasNext()) {
                select.append(", ");
            }
        }
        return select.toString();
    }

    private static List<ColumnType> getColumns(IDynamicEntity entity) {
        return entity.getDynamicType().getColumns();
    }
}
