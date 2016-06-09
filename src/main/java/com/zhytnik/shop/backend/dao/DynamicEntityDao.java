package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.backend.dao.query.DynamicManager;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicEntityDao<T extends IDynamicEntity> {

    protected Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    protected DynamicManager dynamicManager;

    public DynamicEntityDao() {
    }

    public DynamicEntityDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void persist(T entity) {
        entityManager.persist(entity);
        dynamicManager.persist(entity);
        entity.getDynamicAccessor().resetChanges();
    }

    public void update(T entity) {
        entityManager.merge(entity);
        dynamicManager.update(entity);
        entity.getDynamicAccessor().resetChanges();
    }

    public T findById(Long id) {
        final T entity = entityManager.find(clazz, id);
        dynamicManager.load(entity);
        return entity;
    }

    public void delete(Long id) {
        final T entity = entityManager.find(clazz, id);
        entityManager.remove(entity);
        dynamicManager.delete(entity);
    }

    public List<T> loadAll() {
        return findByQuery(getLoadAllQuery());
    }

    public List<T> load(int start, int finish) {
        final Query query = entityManager.createQuery(getLoadAllQuery());
        query.setFirstResult(start).setMaxResults(finish);
        return findByQuery(query);
    }

    private CriteriaQuery<T> getLoadAllQuery() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> query = builder.createQuery(clazz);
        final Root<T> root = query.from(clazz);
        return query.select(root);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByQuery(Query query) {
        final List<T> entities = query.getResultList();
        loadDynamic(entities);
        return entities;
    }

    protected List<T> findByQuery(CriteriaQuery<T> query) {
        final List<T> entities = entityManager.createQuery(query).getResultList();
        loadDynamic(entities);
        return entities;
    }

    protected void loadDynamic(List<T> entities) {
        for (T entity : entities) {
            dynamicManager.load(entity);
        }
    }

    public void setClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Required
    public void setDynamicManager(DynamicManager dynamicManager) {
        this.dynamicManager = dynamicManager;
    }
}
