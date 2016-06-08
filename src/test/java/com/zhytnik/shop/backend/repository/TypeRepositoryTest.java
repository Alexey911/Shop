package com.zhytnik.shop.backend.repository;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.testing.IntegrationTest;
import com.zhytnik.shop.util.TransactionalTest;
import com.zhytnik.shop.util.dataset.ClearSchema;
import com.zhytnik.shop.util.dataset.DataSet;
import com.zhytnik.shop.util.dataset.ExpectedDataSet;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.DATE;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;
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
        final DynamicType type = typeRepository.findOne(EXIST_TYPE);
        assertThat(type).isNotNull();
        assertThat(type.getName()).isEqualTo("type");
        assertThat(type.getFields()).hasSize(1);

        final DynamicField field = type.getFields().get(0);
        assertThat(field.getName()).isEqualTo("field");
        assertThat(field.isRequired()).isTrue();
        assertThat(field.getOrder()).isEqualTo(0);
        assertThat(field.getPrimitiveType()).isEqualTo(LONG);
    }

    @Test
    public void shouldDelete() {
        typeRepository.deleteAll();
        assertThat(typeRepository.count()).isZero();
    }

    @Test
    @ExpectedDataSet("update")
    public void shouldUpdate() {
        final DynamicType type = typeRepository.findOne(EXIST_TYPE);
        type.setName("new name");
        typeRepository.save(type);
    }

    @Test
    @ClearSchema
    @ExpectedDataSet("create")
    public void shouldCreate() {
        final DynamicType type = new DynamicType();
        type.setId(48L);
        type.setName("type1");

        final DynamicField field = new DynamicField();
        field.setName("field");
        field.setId(5L);
        field.setOrder(0);
        field.setPrimitiveType(DATE);
        field.setRequired(true);
        field.setType(type);

        type.setFields(newArrayList(field));

        typeRepository.save(type);
    }
}
