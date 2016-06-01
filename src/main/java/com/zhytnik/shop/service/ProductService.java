package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.TypeCreator;
import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.backend.dao.DynamicEntityDao;
import com.zhytnik.shop.backend.validator.DynamicEntityValidator;
import com.zhytnik.shop.domain.business.market.Product;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class ProductService {

    @Autowired
    private DynamicEntityDao<Product> dao;

    private DynamicEntityValidator validator = new DynamicEntityValidator();

    @Autowired
    private TypeCreator typeCreator;

    public void create(DynamicType type) {
        typeCreator.create(type);
    }

    public Product create() {
        Product p = new Product();
        p.setDynamicAccessor(new DynamicAccessor(p));
        return p;
    }

    public void save(Product p) {
        validator.validate(p);
        dao.persist(p);
    }

    public Product load(Long id) {
        dao.setClass(Product.class);
        return dao.findById(id);
    }

    public void update(Product p) {
        validator.validate(p);
        dao.update(p);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public void setDao(DynamicEntityDao<Product> dao) {
        this.dao = dao;
    }
}
