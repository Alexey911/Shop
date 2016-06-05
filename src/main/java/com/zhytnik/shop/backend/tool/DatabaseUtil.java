package com.zhytnik.shop.backend.tool;

import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class DatabaseUtil {

    private static DatabaseUtil instance;

    private Dialect dialect;
    private DataSource dataSource;

    private DatabaseUtil() {
        instance = this;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DatabaseUtil getInstance() {
        return instance;
    }
}
