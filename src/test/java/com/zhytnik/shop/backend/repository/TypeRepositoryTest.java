package com.zhytnik.shop.backend.repository;

import com.zhytnik.shop.testing.DataSet;
import com.zhytnik.shop.testing.IntegrationTest;
import com.zhytnik.shop.util.TransactionalTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
@Category(IntegrationTest.class)
@DataSet
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
        assertThat(typeRepository.count()).isNotZero();
    }
}
