package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.validator.Validator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exception.InfrastructureException;
import com.zhytnik.shop.exception.NotUniqueException;
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

    private static final Logger logger = Logger.getLogger(TypeCreator.class);

    private JdbcTemplate jdbcTemplate;
    private TableScriptGenerator generator;
    private Validator<DynamicType> validator;

    public void create(DynamicType type) {
        validator.validate(type);
        sortFields(type.getFields());
        createTable(type);
    }

    private void sortFields(List<DynamicField> fields) {
        sort(fields, new Comparator<DynamicField>() {
            @Override
            public int compare(DynamicField a, DynamicField b) {
                return a.getOrder().compareTo(b.getOrder());
            }
        });
    }

    private void createTable(DynamicType type) {
        final String script = generator.generate(type.getName(), type.getFields());
        logger.info("Execute " + script);
        try {
            jdbcTemplate.execute(script);
        } catch (DataAccessException e) {
            logger.error("Fail execute " + script);
            if (e.getCause().getMessage().contains("already exists")) {
                throw new NotUniqueException();
            }
            throw new InfrastructureException(e);
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
