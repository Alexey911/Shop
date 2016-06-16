package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.validator.Validator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exception.InfrastructureException;
import com.zhytnik.shop.exception.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class TypeCreator {

    private static Logger logger = Logger.getLogger(TypeCreator.class);

    private Validator<DynamicType> validator;

    private JdbcTemplate jdbcTemplate;

    private TableScriptGenerator generator;

    public void create(DynamicType type) {
        sortFields(type.getFields());
        validator.validate(type);
        createTable(type);
    }

    private void createTable(DynamicType type) {
        final String script = generator.generate(type.getName(), type.getFields());
        logger.info("Execute " + script);
        try {
            jdbcTemplate.execute(script);
        } catch (DataAccessException e) {
            logger.error("Fail execute " + script);
            if (e.getCause().getMessage().contains("already exists")) {
                throw new ValidationException("Not unique type name!");
            }
            throw new InfrastructureException(e);
        }
    }

    private void sortFields(List<DynamicField> fields) {
        sort(fields, new Comparator<DynamicField>() {
            @Override
            public int compare(DynamicField a, DynamicField b) {
                return a.getOrder().compareTo(b.getOrder());
            }
        });
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
