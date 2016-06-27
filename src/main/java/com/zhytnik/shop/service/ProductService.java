package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.dao.ProductDao;
import com.zhytnik.shop.backend.validator.Validator;
import com.zhytnik.shop.domain.market.product.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class ProductService {

    private ProductDao dao;

    private Validator<Product> validator;

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> loadAll() {
        return dao.loadAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findByKeywords(String... keywords) {
        return dao.findByKeywords(keywords);
    }

    @Transactional
    public Long create(Product product) {
        validator.validate(product);
        dao.persist(product);
        return product.getId();
    }

    @Transactional
    public void remove(Long id) {
        dao.remove(id);
    }

    @Transactional
    public void update(Product p) {
        validator.validate(p);
        dao.update(p);
    }

//    public Product loadByCode(Long code) {
//        return dao.findByCode(code);
//}

//    public List<Product> findByFilter(DynamicType type, Filter filter) {
//        return dao.findByQuery(type, filter);
//    }

    public void setDao(ProductDao dao) {
        this.dao = dao;
    }

    public void setValidator(Validator<Product> validator) {
        this.validator = validator;
    }
}
