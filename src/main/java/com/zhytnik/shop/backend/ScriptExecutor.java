package com.zhytnik.shop.backend;

import com.zhytnik.shop.exeception.InfrastructureException;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@Component
public class ScriptExecutor {

    @Autowired
    private SessionFactory sessionFactory;

    public void execute(String script) {
        Session session = sessionFactory.openSession();
        Transaction t = session.getTransaction();

        try {
            t.begin();
            SQLQuery query = session.createSQLQuery(script);
            query.executeUpdate();
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
            throw new InfrastructureException(e);
        } finally {
            session.close();
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
