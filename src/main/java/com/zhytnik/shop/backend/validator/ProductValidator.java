package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.exception.ValidationException;
import org.springframework.beans.factory.annotation.Required;

import java.util.Set;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class ProductValidator implements Validator<Product> {

    private Validator<IDynamicEntity> dynamicValidator;

    @Override
    public void validate(Product product) {
        if (isNull(product.getDynamicType())) failOnEmptyField("Dynamic type");
        dynamicValidator.validate(product);

//        if (isNull(product.getCode())) failOnEmptyField("Code");
        if (isEmpty(product.getShortName())) failOnEmptyField("Short name");

        if (isNull(product.getTitle()) || !containsTitle(product)) failOnEmptyField("Title");
    }

    private boolean containsTitle(Product product) {
        final Set<MultilanguageTranslation> translations = product.getTitle().getTranslations();
        return translations != null && !translations.isEmpty();
    }

    private void failOnEmptyField(String field) {
        throw new ValidationException("field.empty", field);
    }

    private boolean isNull(Object value) {
        return value == null;
    }

    @Required
    public void setDynamicValidator(Validator<IDynamicEntity> dynamicValidator) {
        this.dynamicValidator = dynamicValidator;
    }
}
