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
        if (!hasChanges(entity)) return;
        final Update update = createUpdate(entity.getDynamicType(), getChangesFields(entity), entity.getId());
        final Query query = entityManager.createNativeQuery(update.toStatementString());
        QueryUtil.fillQueryByFields(query, entity, getChangesFields(entity));
        query.executeUpdate();
    }

    public void remove(IDynamicEntity entity) {
        final Delete delete = createDelete(entity.getDynamicType(), entity.getId());
        entityManager.createNativeQuery(delete.toStatementString()).executeUpdate();
        removeComplexFields(entity);
    }

    private void removeComplexFields(IDynamicEntity entity) {
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
