package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.exeception.ValidationException;
import com.zhytnik.shop.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.zhytnik.shop.backend.validator.LengthValidator.MAX_LENGTH;
import static com.zhytnik.shop.datahelper.DynamicTypeDH.createTypeWithSingleField;
import static com.zhytnik.shop.datahelper.ProductDH.createProductByType;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.STRING;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicEntityValidatorTest {

    static final String ENTITY_FIELD = "field";

    DynamicEntityValidator validator = new DynamicEntityValidator();

    IDynamicEntity entity;
    DynamicType type;

    @Before
    @SuppressWarnings("deprecation")
    public void setUp() {
        type = createTypeWithSingleField();
        entity = createProductByType(type);

        final DynamicField field = getOnlyElement(type.getFields());
        field.setName(ENTITY_FIELD);
        field.setType(STRING);
        field.setRequired(true);
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
