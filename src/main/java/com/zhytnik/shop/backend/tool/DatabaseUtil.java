package com.zhytnik.shop.backend.tool;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class DatabaseUtil {

    private static DatabaseUtil instance;

    private Dialect dialect;
    private DataSource dataSource;
    private SessionFactory sessionFactory;
    private String schema;

    private DatabaseUtil() {
        instance = this;
    }

    public void dropTable(String name) throws DataAccessException {
        new JdbcTemplate(dataSource).execute("DROP TABLE " + name);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public static DatabaseUtil getInstance() {
        return instance;
    }
}
