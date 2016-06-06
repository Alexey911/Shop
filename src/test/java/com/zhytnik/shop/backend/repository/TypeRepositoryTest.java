package com.zhytnik.shop.backend.repository;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.util.data.DataSet;
import com.zhytnik.shop.util.data.ExpectedDataSet;
import com.zhytnik.shop.testing.IntegrationTest;
import com.zhytnik.shop.util.TransactionalTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
@DataSet
@Category(IntegrationTest.class)
public class TypeRepositoryTest extends TransactionalTest {

    final long EXIST_TYPE = 578L;

    @Autowired
    TypeRepository typeRepository;

    @Test
    public void shouldLoad() {
        assertThat(typeRepository.findOne(EXIST_TYPE)).isNotNull();
    }

    @Test
    public void shouldDelete() {
        typeRepository.deleteAll();
        assertThat(typeRepository.count()).isZero();
    }

    @Test
    @Commit
    @ExpectedDataSet
    public void updates() {
        final DynamicType type = typeRepository.findOne(EXIST_TYPE);
        type.setName("new name");
        typeRepository.save(type);
    }
}
