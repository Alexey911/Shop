package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exception.ValidationException;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicTypeValidator implements Validator<DynamicType> {

    private Validator<String> nameValidator;

    @Override
    public void validate(DynamicType type) throws ValidationException {
        failOnUpperCase(type.getName());
        checkFieldOrder(type.getFields());
        nameValidator.validate(type.getName());

        final Set<String> names = newHashSet();
        for (final DynamicField field : type.getFields()) {
            failOnNotSeatedType(field);

            final String name = field.getName();
            nameValidator.validate(name);
            failOnNotUnique(names, name);
            names.add(name);
        }
    }

    private void failOnUpperCase(String name) {
        if (!name.toLowerCase().equals(name)) {
            throw new ValidationException("type.name.should.be.lower");
        }
    }

    private void failOnNotUnique(Set<String> names, String name) {
        if (names.contains(name)) {
            throw new ValidationException("not.unique.field.name");
        }
    }

    private void checkFieldOrder(List<DynamicField> fields) {
        final Set<Integer> positions = newHashSet();
        for (DynamicField field : fields) {
            if (field.getOrder() == null)
                throw new ValidationException("Empty field order");
            positions.add(field.getOrder());
        }
        for (int i = 0; i < fields.size(); i++) {
            if (!positions.contains(i))
                throw new ValidationException("wrong.field.order");
        }
    }

    private void failOnNotSeatedType(DynamicField field) {
        if (field.getPrimitiveType() == null) {
            throw new ValidationException(format("Type not seated on \"%s\" field", field.getName()));
        }
    }

    @Required
    public void setNameValidator(Validator<String> validator) {
        this.nameValidator = validator;
    }
}
