package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.backend.util.StringUtil;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.exeception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static com.zhytnik.shop.backend.validator.LengthValidator.MAX_LENGTH;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.STRING;
import static java.util.Collections.singletonList;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicEntityValidatorTest {

    static final String ENTITY_FIELD = "field";

    DynamicEntityValidator validator = new DynamicEntityValidator();

    IDynamicEntity entity;

    DynamicType type = new DynamicType();

    @Before
    @SuppressWarnings("deprecation")
    public void setUp() {
        entity = new Product();
        entity.setDynamicFieldsValues(new Object[1]);
        entity.setDynamicType(type);

        type.setName("type_1");

        final DynamicField field = new DynamicField();
        field.setName(ENTITY_FIELD);
        field.setRequired(true);
        field.setOrder(0);
        field.setType(STRING);

        type.setFields(singletonList(field));
    }

    @Test
    public void validates() {
        entity.getDynamicAccessor().set(ENTITY_FIELD, "some valid value");
        validator.validate(entity);
    }

    @Test(expected = ValidationException.class)
    public void failsWithEmptyRequiredField() {
        entity.getDynamicAccessor().set(ENTITY_FIELD, null);
        validator.validate(entity);
    }

    @Test(expected = ValidationException.class)
    public void checkComplexTypes() {
        entity.getDynamicAccessor().set(ENTITY_FIELD, StringUtil.generateByLength(MAX_LENGTH + 1));
        validator.validate(entity);
    }
}
