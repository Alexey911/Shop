package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.exception.InfrastructureException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class DatabaseUtil {

    private static Logger logger = Logger.getLogger(DatabaseUtil.class);

    private static DatabaseUtil instance;

    private Dialect dialect;
    private DataSource dataSource;
    private SessionFactory sessionFactory;
    private String schema;

    private DatabaseUtil() {
        instance = this;
    }

    public void dropTable(String name) throws DataAccessException {
        logger.info("Drop table " + name);
        try {
            sessionFactory.getCurrentSession().createSQLQuery("DROP TABLE " + name).executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
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
