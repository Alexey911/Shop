package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.exception.ValidationException;

import java.util.List;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.getDynamicValues;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicEntityValidator implements Validator<IDynamicEntity> {

    private LengthValidator lengthValidator = new LengthValidator();

    //TODO: validate dynamic
    @Override
    public void validate(IDynamicEntity entity) {
        final List<DynamicField> columns = entity.getDynamicType().getFields();
        final Object[] values = getDynamicValues(entity);

        for (int i = 0; i < columns.size(); i++) {
            final DynamicField type = columns.get(i);
            final Object value = values[i];

            if (type.isRequired() && value == null) {
                failOnEmptyRequiredField(type);
            }
            if (type.getPrimitiveType() == PrimitiveType.TEXT) {
                lengthValidator.validate((String) value);
            }
        }
    }

    private void failOnEmptyRequiredField(DynamicField type) {
        throw new ValidationException(format("Required field \"%s\" is empty", type.getName()));
    }
}
