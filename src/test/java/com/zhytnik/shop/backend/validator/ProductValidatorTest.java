package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.exeception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class ProductValidatorTest {

    ProductValidator validator = new ProductValidator();

    Validator<IDynamicEntity> dynamicValidator = spy(new DynamicEntityValidator());

    Product product = new Product();

    DynamicType type = new DynamicType();

    @Before
    public void setUp() {
        validator.setDynamicValidator(dynamicValidator);
        type.setFields(Collections.<DynamicField>emptyList());

        final MultilanguageString title = new MultilanguageString();
        title.add(new MultilanguageTranslation());
        product.setTitle(title);
        product.setDynamicType(type);
        product.setCode(88L);
        product.setShortName("product_1");
    }

    @Test
    public void validates() {
        validator.validate(product);
    }

    @Test
    public void validatesWithDynamicValidator() {
        validator.validate(product);
        verify(dynamicValidator).validate(product);
    }

    @Test(expected = ValidationException.class)
    public void validatesCode() {
        product.setCode(null);
        validator.validate(product);
    }

    @Test(expected = ValidationException.class)
    public void validatesShortName() {
        product.setShortName(null);
        validator.validate(product);
    }

    @Test(expected = ValidationException.class)
    public void shouldContainsAtLeastOneTitle() {
        product.setTitle(new MultilanguageString());
        validator.validate(product);
    }
}
