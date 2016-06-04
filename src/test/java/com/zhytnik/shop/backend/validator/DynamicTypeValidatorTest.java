package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exeception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.zhytnik.shop.datahelper.DynamicTypeDH.createTypeWithSingleField;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicTypeValidatorTest {

    DynamicTypeValidator validator = new DynamicTypeValidator();

    DynamicType type;
    DynamicField field;

    @Before
    public void setUp() {
        validator.setNameValidator(new NameValidator());
        type = createTypeWithSingleField();
        field = getOnlyElement(type.getFields());
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
