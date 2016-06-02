package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.domain.business.market.Product;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
@Component
public class ProductDao extends DynamicEntityDao<Product> {

    public ProductDao() {
        setClass(Product.class);
    }
}
