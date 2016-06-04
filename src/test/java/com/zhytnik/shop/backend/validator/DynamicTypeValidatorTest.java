package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exeception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;
import static java.util.Collections.singletonList;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicTypeValidatorTest {

    DynamicTypeValidator validator = new DynamicTypeValidator();

    DynamicType type = new DynamicType();
    DynamicField field = new DynamicField();

    @Before
    public void setUp() {
        validator.setNameValidator(new NameValidator());

        type.setName("type_01");
        type.setFields(singletonList(field));

        field.setName("field");
        field.setOrder(0);
        field.setType(LONG);
    }

    @Test
    public void validates() {
        validator.validate(type);
    }

    @Test(expected = ValidationException.class)
    public void validatesName() {
        type.setName("not valid name");
        validator.validate(type);
    }

    @Test(expected = ValidationException.class)
    public void validateFieldsTypes() {
        field.setType(null);
        validator.validate(type);
    }

    @Test(expected = ValidationException.class)
    public void validateFieldsNames() {
        field.setName("not_valid_field_name!");
        validator.validate(type);
    }

    @Test(expected = ValidationException.class)
    public void validateFieldsOrder() {
        field.setOrder(1);
        validator.validate(type);
    }
}
