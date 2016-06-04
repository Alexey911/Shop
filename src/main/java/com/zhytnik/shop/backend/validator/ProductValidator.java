package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.exeception.InfrastructureException;
import com.zhytnik.shop.exeception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@Component
public class ProductValidator implements Validator<Product> {

    @Autowired
    private DynamicEntityValidator dynamicValidator;

    @Override
    public void validate(Product product) {
        if (isNull(product.getDynamicType())) {
            throw new InfrastructureException();
        }
        dynamicValidator.validate(product);

        if (isNull(product.getCode())) failOnEmptyField("Code");
        if (isEmpty(product.getShortName())) failOnEmptyField("Short name");

        if (isNull(product.getTitle()) || product.getTitle().getTranslations().isEmpty()) {
            failOnEmptyField("Title");
        }
    }

    private void failOnEmptyField(String field) {
        throw new ValidationException(String.format("Field \"%s\" is empty", field));
    }

    private boolean isNull(Object value) {
        return value == null;
    }
}
