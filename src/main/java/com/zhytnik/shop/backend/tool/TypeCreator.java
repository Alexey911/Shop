package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.validator.DynamicTypeValidator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class TypeCreator {

    @Autowired
    private DynamicTypeValidator validator;

    @Autowired
    private TableCreator creator;

    public void create(DynamicType type) {
        initialOrder(type.getFields());
        validator.validate(type);
        creator.createTable(type);
    }

    private void initialOrder(List<DynamicField> columns) {
        int order = 0;
        for (DynamicField column : columns) {
            column.setOrder(order++);
        }
    }
}
