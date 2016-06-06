package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.domain.market.product.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.zhytnik.shop.backend.tool.Asserts.failOnEmpty;
import static java.util.Arrays.asList;
import static org.hibernate.persister.collection.CollectionPropertyNames.COLLECTION_ELEMENTS;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class ProductDao extends DynamicEntityDao<Product> {

    public ProductDao() {
        setClass(Product.class);
    }

    public List<Product> findByKeywords(String... keywords) {
        final Session session = openSession();
        try {
            final Criteria c = session.createCriteria(clazz, "product");
            c.createAlias("product.keywords", "keyword");
            c.add(Restrictions.in("keyword." + COLLECTION_ELEMENTS, asList(keywords)));
            return findByCriteria(session, c);
        } finally {
            closeSession(session);
        }
    }

    public Product findByCode(Long code) {
        final Session session = openSession();
        try {
            final Criteria c = session.createCriteria(clazz, "product");
            c.add(Restrictions.eq("product.code", code));

            final List<Product> entities = findByCriteria(session, c);
            failOnEmpty(entities);
            return entities.get(0);
        } finally {
            closeSession(session);
        }
    }
}
