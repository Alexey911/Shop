package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class DynamicEntityDao<T extends IDynamicEntity> {

    private HibernateTemplate template;

    private Class<T> clazz;

    public DynamicEntityDao() {
    }

    public DynamicEntityDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void initialize(T entity) {
    }

    public void persist(T entity) {
        template.persist(entity);
    }

    public T findById(Long id) {
        return template.get(clazz, id);
    }

    public void delete(T entity) {
        template.delete(entity);
    }

    public void update(T entity) {
        template.update(entity);
    }

    public List<T> getAll() {
        return template.loadAll(clazz);
    }

    @SuppressWarnings("unchecked")
    public List<T> load(int start, int finish) {
        Session session = template.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.setFirstResult(start);
        criteria.setMaxResults(finish);
        return criteria.list();
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
