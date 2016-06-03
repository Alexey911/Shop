package com.zhytnik.shop.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DomainObjectUtil {

    private static DomainObjectUtil instance;

    private ThreadLocal<Boolean> generate = new ThreadLocal<>();

    @Autowired
    private DataSource dataSource;

    private DomainObjectUtil() {
        instance = this;
    }

    public Long getNextId() {
        if (!isGenerateEnable()) return null;
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.queryForObject("SELECT ID_SEQUENCE.nextval FROM DUAL", Long.class);
    }

    private boolean isGenerateEnable() {
        final Boolean enable = generate.get();
        return enable == null || enable;
    }

    public void setGenerate(boolean value) {
        generate.set(value);
    }

    public void resetGenerate() {
        generate.remove();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DomainObjectUtil getInstance() {
        return instance;
    }
}
