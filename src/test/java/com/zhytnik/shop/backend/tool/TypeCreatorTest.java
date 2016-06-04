package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.validator.DynamicTypeValidator;
import com.zhytnik.shop.backend.validator.NameValidator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;
import static java.util.Collections.singletonList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class TypeCreatorTest {

    @Spy
    @InjectMocks
    TypeCreator typeCreator = new TypeCreator();

    DynamicType type = new DynamicType();

    @Spy
    DynamicField field = new DynamicField();

    @Spy
    DynamicTypeValidator dynamicValidator = new DynamicTypeValidator();

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    TableScriptGenerator tableGenerator;

    @Before
    public void setUp() {
        dynamicValidator.setNameValidator(new NameValidator());
        typeCreator.setValidator(dynamicValidator);

        field.setName("field");
        field.setType(LONG);

        type.setFields(singletonList(field));
        type.setName("type");
    }

    @Test
    public void creates() {
        typeCreator.create(type);
    }

    @Test
    public void setsOwnFieldsOrder() {
        typeCreator.create(type);
        verify(field).setOrder(anyInt());
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
