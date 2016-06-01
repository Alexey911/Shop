package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.zhytnik.shop.backend.dao.DynamicUtil.*;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class DynamicEntityDao<T extends IDynamicEntity> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> clazz;

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
