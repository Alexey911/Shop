package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.backend.dao.search.Filter;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.zhytnik.shop.backend.dao.DynamicUtil.*;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicEntityDao<T extends IDynamicEntity> {

    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> clazz;

    public DynamicEntityDao() {
    }

    public DynamicEntityDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void persist(T entity) {
        final Session session = openSessionWithTransaction();
        persistDynamic(session, entity);
        session.persist(entity);
        closeSessionWithTransaction(session);
        entity.getDynamicAccessor().resetChanges();
    }

    public void update(T entity) {
        final Session session = openSessionWithTransaction();
        updateDynamic(session, entity);
        session.update(entity);
        closeSessionWithTransaction(session);
        entity.getDynamicAccessor().resetChanges();
    }

    public void delete(Long id) {
        final Session session = openSessionWithTransaction();
        final T entity = session.load(clazz, id);
        deleteDynamic(session, entity);
        session.delete(entity);
        closeSessionWithTransaction(session);
    }

    public T findById(Long id) {
        final Session session = openSession();
        final T entity = session.load(clazz, id);
        loadDynamic(session, entity);
        closeSession(session);
        return entity;
    }

    public List<T> loadAll() {
        final Session session = openSession();
        return findByCriteria(session, session.createCriteria(clazz));
    }

    public List<T> load(int start, int finish) {
        final Session session = openSession();
        final Criteria criteria = session.createCriteria(clazz);
        criteria.setFirstResult(start);
        criteria.setMaxResults(finish);
        return findByCriteria(session, criteria);
    }

    public List<T> findByQuery(DynamicType type, Filter filter) {
        if (filter.isEmpty()) {
            return loadAll();
        }
        final Session session = openSession();
        final List<T> entities = DynamicUtil.findByQuery(clazz, session, type, filter);
        closeSession(session);
        return entities;
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Session session, Criteria criteria) {
        final List<T> entities = criteria.list();
        for (T entity : entities) {
            loadDynamic(session, entity);
        }
        closeSession(session);
        return entities;
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    protected Session openSessionWithTransaction() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    protected void closeSession(Session session) {
        session.close();
    }

    protected void closeSessionWithTransaction(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    public void setClass(Class<T> clazz) {
        this.clazz = clazz;
    }
}
