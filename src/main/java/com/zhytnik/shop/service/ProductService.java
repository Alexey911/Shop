package com.zhytnik.shop.service;

import com.zhytnik.shop.backend.dao.ProductDao;
import com.zhytnik.shop.backend.dao.search.Filter;
import com.zhytnik.shop.backend.tool.HistoryUtil;
import com.zhytnik.shop.backend.validator.Validator;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.product.Product;

import java.util.List;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.setDynamicValues;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class ProductService {

    private ProductDao dao;

    private Validator<Product> validator;

    public Product createByType(DynamicType type) {
        final Product product = new Product();
        product.setDynamicType(type);

        final int dynamicFieldCount = type.getFields().size();
        setDynamicValues(product, new Object[dynamicFieldCount]);
        HistoryUtil.setUp(product);
        return product;
    }

    public void save(Product p) {
        validator.validate(p);
        dao.persist(p);
    }

    public Product loadById(Long id) {
        return dao.findById(id);
    }

    public Product loadByCode(Long code) {
        return dao.findByCode(code);
    }

    public void update(Product p) {
        validator.validate(p);
        dao.update(p);
    }

    public List<Product> loadAll() {
        return dao.loadAll();
    }

    public List<Product> findByKeywords(String... keywords) {
        return dao.findByKeywords(keywords);
    }

    public List<Product> findByFilter(DynamicType type, Filter filter) {
        return dao.findByQuery(type, filter);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public void setDao(ProductDao dao) {
        this.dao = dao;
    }

    public void setValidator(Validator<Product> validator) {
        this.validator = validator;
    }
}
