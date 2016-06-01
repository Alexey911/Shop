package com.zhytnik.shop.backend;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import org.hibernate.dialect.Oracle10gDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
@Component
public class TableCreator {

    @Autowired
    private ScriptExecutor scriptExecutor;

    private TableScriptGenerator generator = new TableScriptGenerator();

    public void createTable(DynamicType type) {
        generator.setDialect(new Oracle10gDialect());
        final String script = generator.generate(type.getName(), type.getColumns());
        scriptExecutor.execute(script);
    }

    public void setGenerator(TableScriptGenerator generator) {
        this.generator = generator;
    }
}
