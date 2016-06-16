package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.validator.DynamicTypeValidator;
import com.zhytnik.shop.backend.validator.NameValidator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exception.ValidationException;
import com.zhytnik.shop.testing.UnitTest;
import org.hibernate.dialect.Oracle10gDialect;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class TypeCreatorTest {

    @Spy
    @InjectMocks
    TypeCreator typeCreator;

    DynamicType type = new DynamicType();

    @Spy
    DynamicField field = new DynamicField();

    @Spy
    DynamicTypeValidator dynamicValidator = new DynamicTypeValidator();

    @Mock
    JdbcTemplate jdbcTemplate;

    @Spy
    TableScriptGenerator tableGenerator = new TableScriptGenerator();

    @Before
    public void setUp() {
        tableGenerator.setDialect(new Oracle10gDialect());

        dynamicValidator.setNameValidator(new NameValidator());
        typeCreator.setValidator(dynamicValidator);

        field.setName("field");
        field.setPrimitiveType(LONG);
        field.setOrder(0);

        type.setFields(singletonList(field));
        type.setName("type");
    }

    @Test
    public void creates() {
        typeCreator.create(type);
    }

    @Test
    public void usingCustomFieldOrder() {
        final int orderBefore = field.getOrder();
        typeCreator.create(type);
        assertThat(orderBefore).isEqualTo(field.getOrder());
    }

    @Test(expected = ValidationException.class)
    public void fieldsMustHaveRightOrder() {
        field.setOrder(null);
        typeCreator.create(type);
    }

    @Test
    public void createBySteps() {
        typeCreator.create(type);
        InOrder order = inOrder(field, dynamicValidator, tableGenerator, jdbcTemplate);
        order.verify(field).setOrder(anyInt());
        order.verify(dynamicValidator).validate(type);
        order.verify(tableGenerator).generate(type.getName(), type.getFields());
        order.verify(jdbcTemplate).execute(anyString());
        order.verifyNoMoreInteractions();
    }
}
