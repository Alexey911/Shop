package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.ColumnType;
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

        for (ColumnType column : type.getColumns()) {
            validator.validate(column.getName());
        }

        for (ColumnType column : type.getColumns()) {
            if (column.getType() == null) {
                failOnNotSeatedType(column);
            }
        }

    }

    private void failOnNotSeatedType(ColumnType column) {
        throw new ValidationException(format("Type not seated on \"%s\" column", column.getName()));
    }
}
