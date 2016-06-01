package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.domain.dynamic.ColumnType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.exeception.ValidationException;

import java.util.List;

import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DynamicEntityValidator implements Validator<IDynamicEntity> {

    private LengthValidator lengthValidator = new LengthValidator();

    @Override
    public void validate(IDynamicEntity entity) {
        final List<ColumnType> columns = entity.getDynamicType().getColumns();
        final Object[] values = entity.getDynamicFieldsValues();

        for (int i = 0; i < columns.size(); i++) {
            final ColumnType type = columns.get(i);
            final Object value = values[i];

            if (type.isRequired() && value == null) {
                failOnEmptyRequiredField(type);
            }
            if (type.getType() == PrimitiveType.STRING) {
                lengthValidator.validate((String) value);
            }
        }
    }

    private void failOnEmptyRequiredField(ColumnType type) {
        throw new ValidationException(format("Required field \"%s\" is empty", type.getName()));
    }
}
