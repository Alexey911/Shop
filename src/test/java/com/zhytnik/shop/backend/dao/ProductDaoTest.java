package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.backend.repository.CategoryRepository;
import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.testing.IntegrationTest;
import com.zhytnik.shop.util.TransactionalTest;
import com.zhytnik.shop.util.dataset.DataSet;
import com.zhytnik.shop.util.dataset.ExpectedDataSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.zhytnik.shop.datahelper.ProductDH.createProductByType;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 08.06.2016
 */
@SqlGroup({@Sql("create-type.sql")})
@org.junit.experimental.categories.Category(IntegrationTest.class)
public class ProductDaoTest extends TransactionalTest {

    final static long PRODUCT = 85L;
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
    @DataSet("type")
    @ExpectedDataSet("persist")
    public void shouldPersist() {
        final Product product = createProductByType(typeRepository.findOne(TYPE));
        product.setCategory(categoryRepository.findOne(CATEGORY));
        product.getDynamicAccessor().set(DYNAMIC_FIELD, 5L);
        productDao.persist(product);
    }

    @Test
    @DataSet("load")
    public void shouldLoad() {
        final Product p = productDao.findById(PRODUCT);
        assertThat(p).isNotNull();
        assertThat(p.getTitle()).isNotNull();
        assertThat(p.getDescription()).isNotNull();
        assertThat(p.getCategory().getId()).isEqualTo(CATEGORY);
        assertThat(p.getDynamicType().getId()).isEqualTo(TYPE);
        assertThat(p.getCode()).isEqualTo(88L);
        assertThat(p.getVersion()).isEqualTo(0L);
        assertThat(p.getShortName()).isEqualTo("product1");
        assertThat(p.getKeywords()).hasSize(2).containsOnly("hot", "best");
        assertThat(p.getComments()).hasSize(1);
        assertThat(p.getDynamicAccessor().get(DYNAMIC_FIELD)).isEqualTo(46L);
    }

    @Test
    @DataSet("load")
    @ExpectedDataSet("update")
    public void shouldUpdate() {
        final Product p = productDao.findById(PRODUCT);
        getOnlyElement(p.getTitle().getTranslations()).setTranslation("new translation");
        p.getDynamicAccessor().set(DYNAMIC_FIELD, 47L);
        productDao.update(p);
    }

    @Test
    @Commit
    @DataSet("load")
    public void shouldDelete() {
        productDao.remove(PRODUCT);
        assertThat(productDao.loadAll()).isEmpty();
    }

    @Test
    @DataSet("load")
    public void loadsAll() {
        assertThat(productDao.loadAll()).hasSize(1);
    }

    @Test
    @DataSet("load")
    public void loadsByKeywords() {
        assertThat(productDao.findByKeywords("hot")).isNotEmpty();
    }

    @Test
    @DataSet("load")
    public void loadsFromInterval() {
        assertThat(productDao.load(0, 1)).hasSize(1);
    }
}
