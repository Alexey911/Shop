package com.zhytnik.shop.backend.dao.query;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.domain.text.MultilanguageString;
import org.hibernate.sql.Delete;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Select;
import org.hibernate.sql.Update;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.*;
import static com.zhytnik.shop.backend.dao.query.QueryBuilder.*;
import static com.zhytnik.shop.backend.dao.query.QueryUtil.fillDynamic;
import static com.zhytnik.shop.domain.DomainObjectUtil.areEqual;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicManager {

    @PersistenceContext
    private EntityManager entityManager;

    private DynamicManager() {
    }

    private DynamicManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(IDynamicEntity entity) {
        final Insert insert = createInsert(entity.getDynamicType(), entity.getId());
        final Query query = entityManager.createNativeQuery(insert.toStatementString());
        for (Object item : getDynamicValues(entity)) persistIfNotPrimitive(item);
        QueryUtil.fillQuery(query, entity);
        query.executeUpdate();
    }

    private void persistIfNotPrimitive(Object item) {
        if (item instanceof IDomainObject) {
            entityManager.persist(item);
        }
    }

    public void update(IDynamicEntity entity) {
        final Update update = createUpdate(entity.getDynamicType(), entity.getId());
        final Query query = entityManager.createNativeQuery(update.toStatementString());

        final Select select = createSelect(entity);
        final Query selectQuery = entityManager.createNativeQuery(select.toStatementString());
        //TODO: work with any args count (now works only with args > 1)
        final Object[] lastValues = (Object[]) selectQuery.getSingleResult();

        final int size = entity.getDynamicType().getFields().size();
        final Object[] values = getDynamicValues(entity);
        for (int i = 0; i < size; i++) {
            final Object item = values[i];
            if (item instanceof IDomainObject) {
                if (areEqual(asDomainObject(lastValues[i]), asDomainObject(item)))
                    updateIfNotPrimitive(item);
                else
                    persistIfNotPrimitive(item);
            }
        }
        QueryUtil.fillQuery(query, entity);
        query.executeUpdate();
    }


    private IDomainObject asDomainObject(Object object) {
        return (IDomainObject) object;
    }

    private void updateIfNotPrimitive(Object item) {
        if (item instanceof IDomainObject) {
            entityManager.merge(item);
        }
    }

    public void remove(IDynamicEntity entity) {
        final Delete delete = createDelete(entity.getDynamicType(), entity.getId());
        entityManager.createNativeQuery(delete.toStatementString()).executeUpdate();
        removeIfNotPrimitive(entity);
    }

    private void removeIfNotPrimitive(IDynamicEntity entity) {
        for (DynamicField field : entity.getDynamicType().getFields()) {
            if (field.getPrimitiveType() == PrimitiveType.STRING) {
                final MultilanguageString str = (MultilanguageString) getDynamicValue(entity, field.getOrder());
                entityManager.remove(str);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void load(IDynamicEntity entity) {
        final Select select = createSelect(entity);
        final Query query = entityManager.createNativeQuery(select.toStatementString());
        final Object values = query.getSingleResult();
        fillDynamic(values, entity);
        loadNotSimpleFields(entity);
    }

    private void loadNotSimpleFields(IDynamicEntity entity) {
        for (DynamicField field : entity.getDynamicType().getFields()) {
            final PrimitiveType type = field.getPrimitiveType();
            if (type == PrimitiveType.STRING) {
                final Long strID = (Long) getDynamicValue(entity, field.getOrder());
                final MultilanguageString str = entityManager.find(MultilanguageString.class, strID);
                setDynamicValue(entity, field.getOrder(), str);
            }
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
