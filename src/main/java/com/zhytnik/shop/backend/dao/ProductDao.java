package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.domain.market.product.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hibernate.persister.collection.CollectionPropertyNames.COLLECTION_ELEMENTS;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
@Component
public class ProductDao extends DynamicEntityDao<Product> {

    public ProductDao() {
        setClass(Product.class);
    }

    public List<Product> findByKeywords(String... keywords) {
        final Session session = openSession();
        final Criteria criteria = session.createCriteria(clazz, "product");
        criteria.createAlias("product.keywords", "keyword");
        criteria.add(Restrictions.in("keyword." + COLLECTION_ELEMENTS, asList(keywords)));
        return findByCriteria(session, criteria);
    }
}
