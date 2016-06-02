package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exeception.ValidationException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class DynamicTypeValidator implements Validator<DynamicType> {

    private NameValidator validator = new NameValidator();

    @Override
    public void validate(DynamicType type) throws ValidationException {
        validator.validate(type.getName());

        for (DynamicField column : type.getFields()) {
            validator.validate(column.getName());
            if (column.getType() == null) {
                failOnNotSeatedType(column);
            }
        }
    }

    private void failOnNotSeatedType(DynamicField column) {
        throw new ValidationException(format("Type not seated on \"%s\" column", column.getName()));
    }
}
