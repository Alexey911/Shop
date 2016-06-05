package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.validator.Validator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class TypeCreator {

    @Autowired
    private Validator<DynamicType> validator;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TableScriptGenerator generator;

    public void create(DynamicType type) {
        initialOrder(type.getFields());
        validator.validate(type);
        createTable(type);
    }

    private void createTable(DynamicType type) {
        final String script = generator.generate(type.getName(), type.getFields());
        jdbcTemplate.execute(script);
    }

    private void initialOrder(List<DynamicField> columns) {
        int order = 0;
        for (DynamicField column : columns) {
            column.setOrder(order++);
        }
    }

    public void setValidator(Validator<DynamicType> validator) {
        this.validator = validator;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setGenerator(TableScriptGenerator generator) {
        this.generator = generator;
    }
}
