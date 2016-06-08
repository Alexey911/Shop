package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.backend.repository.CategoryRepository;
import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.testing.IntegrationTest;
import com.zhytnik.shop.util.TransactionalTest;
import com.zhytnik.shop.util.dataset.DataSet;
import com.zhytnik.shop.util.dataset.ExpectedDataSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.zhytnik.shop.datahelper.ProductDH.createProductByType;

/**
 * @author Alexey Zhytnik
 * @since 08.06.2016
 */
@SqlGroup(@Sql("type.sql"))
@DataSet
@org.junit.experimental.categories.Category(IntegrationTest.class)
public class ProductDaoTest extends TransactionalTest {

    final static long TYPE = 578L;
    final static long CATEGORY = 37L;

    final static String DYNAMIC_FIELD = "my_field";

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductDao productDao;

    @Test
    @ExpectedDataSet("persist")
    public void shouldPersist() {
        final Product product = createProductByType(typeRepository.findOne(TYPE));
        product.setCategory(categoryRepository.findOne(CATEGORY));
        fillDynamic(product);
        productDao.persist(product);
    }

    void fillDynamic(Product product) {
        final DynamicAccessor accessor = product.getDynamicAccessor();
        accessor.set(DYNAMIC_FIELD, 5L);
    }
}
