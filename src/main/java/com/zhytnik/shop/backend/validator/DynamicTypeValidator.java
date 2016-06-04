package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exeception.ValidationException;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicTypeValidator implements Validator<DynamicType> {

    private Validator<String> nameValidator;

    @Override
    public void validate(DynamicType type) throws ValidationException {
        nameValidator.validate(type.getName());

        final List<DynamicField> fields = type.getFields();
        for (int i = 0; i < fields.size(); i++) {
            final DynamicField field = fields.get(i);

            if (field.getOrder() != i) failOnWrongFieldsOrder();
            if (field.getType() == null) failOnNotSeatedType(field);
            nameValidator.validate(field.getName());
        }
    }

    private void failOnWrongFieldsOrder() {
        throw new ValidationException("Wrong fields order");
    }

    private void failOnNotSeatedType(DynamicField column) {
        throw new ValidationException(format("Type not seated on \"%s\" column", column.getName()));
    }

    @Required
    public void setNameValidator(Validator<String> validator) {
        this.nameValidator = validator;
    }
}
