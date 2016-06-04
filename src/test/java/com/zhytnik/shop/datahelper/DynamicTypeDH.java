package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;

import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;
import static java.util.Collections.singletonList;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicTypeDH {

    private DynamicTypeDH() {
    }

    public static DynamicType createTypeWithSingleField() {
        final DynamicType type = new DynamicType();
        type.setName("type_01");
        type.setFields(singletonList(createField()));
        return type;
    }

    private static DynamicField createField() {
        final DynamicField field = new DynamicField();
        field.setName("field");
        field.setOrder(0);
        field.setType(LONG);
        return field;
    }
}
