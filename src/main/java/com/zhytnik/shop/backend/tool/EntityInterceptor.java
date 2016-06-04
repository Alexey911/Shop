package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.domain.DomainObject;
import com.zhytnik.shop.domain.DomainObjectUtil;
import com.zhytnik.shop.domain.IDomainObject;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;

import java.io.Serializable;

/**
 * @author Alexey Zhytnik
 * @since 03.06.2016
 */
class EntityInterceptor extends EmptyInterceptor {

    private DomainObjectUtil util;

    @Override
    public Object instantiate(String entityName, EntityMode entityMode, Serializable id) {
        try {
            Class clazz = Class.forName(entityName);
            if (DomainObject.class.isAssignableFrom(clazz)) {
                util.setGenerate(false);
                Object entity = clazz.newInstance();
                ((IDomainObject) entity).setId((Long) id);
                return entity;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            util.resetGenerate();
        }
        return super.instantiate(entityName, entityMode, id);
    }

    public void setDomainObjectUtil(DomainObjectUtil util) {
        this.util = util;
    }
}
