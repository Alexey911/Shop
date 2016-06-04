package com.zhytnik.shop.backend.dao.query;

import com.zhytnik.shop.backend.dao.search.Filter;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.sql.Delete;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Select;
import org.hibernate.sql.Update;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.backend.access.DynamicAccessUtil.*;
import static com.zhytnik.shop.backend.dao.query.QueryBuilder.*;
import static com.zhytnik.shop.backend.dao.query.QueryUtil.setResultTransformByType;
import static com.zhytnik.shop.backend.dao.query.QueryUtil.transformQueryData;
import static com.zhytnik.shop.backend.dao.query.SearchBuilder.buildQuery;
import static com.zhytnik.shop.backend.tool.Asserts.failOnEmpty;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicManager {

    private DynamicManager() {
    }

    public static void persistDynamic(Session session, IDynamicEntity entity) {
        final Insert insert = createInsert(entity.getDynamicType(), entity.getId());
        final SQLQuery query = session.createSQLQuery(insert.toStatementString());
        QueryUtil.fillQuery(query, entity);
        query.executeUpdate();
    }

    public static void updateDynamic(Session session, IDynamicEntity entity) {
        if (!hasChanges(entity)) return;
        final Update update = createUpdate(entity.getDynamicType(), getChangesFields(entity), entity.getId());
        final SQLQuery query = session.createSQLQuery(update.toStatementString());
        QueryUtil.fillQueryByFields(query, entity, getChangesFields(entity));
        query.executeUpdate();
    }

    public static void deleteDynamic(Session session, IDynamicEntity entity) {
        final Delete delete = createDelete(entity.getDynamicType(), entity.getId());
        final SQLQuery query = session.createSQLQuery(delete.toStatementString());
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public static void loadDynamic(Session session, IDynamicEntity entity) {
        final Select select = createSelect(entity);
        final SQLQuery query = session.createSQLQuery(select.toStatementString());
        setResultTransformByType(query, entity.getDynamicType());

        final List<List<?>> entities = query.list();
        failOnEmpty(entities);

        setDynamicValues(entity, transformQueryData(entities.get(0), 0));
    }

    @SuppressWarnings("unchecked")
    public static <T extends IDynamicEntity> List<T> findByQuery(Class<T> clazz, Session session,
                                                                 DynamicType type, Filter filter) {
        final SQLQuery query = buildQuery(session, filter, type);
        final List<List<?>> rowEntities = query.list();

        final List<T> entities = newArrayList();
        for (List<?> rowEntity : rowEntities) {
            final Long id = (Long) rowEntity.get(0);
            final T entity = session.load(clazz, id);
            setDynamicValues(entity, transformQueryData(rowEntity, 1));
            entities.add(entity);
        }
        return entities;
    }
}
