package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.dao.ProductDao;
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
    private ProductDao dao;

    private DynamicEntityValidator validator = new DynamicEntityValidator();

    public Product createByType(DynamicType type) {
        final Product product = new Product();
        product.setDynamicType(type);

        final int dynamicFieldCount = type.getFields().size();
        product.setDynamicFieldsValues(new Object[dynamicFieldCount]);
        return product;
    }

    public void save(Product p) {
        validator.validate(p);
        dao.persist(p);
    }

    public Product load(Long id) {
        return dao.findById(id);
    }

    public void update(Product p) {
        validator.validate(p);
        dao.update(p);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public void setDao(ProductDao dao) {
        this.dao = dao;
    }
}
