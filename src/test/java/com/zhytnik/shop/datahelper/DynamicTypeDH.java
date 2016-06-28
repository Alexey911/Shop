package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.datahelper.MultiStringDH.createString;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicTypeDH {

    private DynamicTypeDH() {
    }

    public static DynamicType createTypeWithSingleField() {
        final DynamicType type = new DynamicType();
        type.setId(45L);
        type.setName("type_01");
        type.setFields(newArrayList(createField()));
        return type;
    }

    private static DynamicField createField() {
        final DynamicField field = new DynamicField();
        field.setId(75L);
        field.setOrder(0);
        field.setName("field");
        field.setPrimitiveType(LONG);
        field.setDescription(createString());
        return field;
    }
}
