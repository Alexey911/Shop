package com.zhytnik.shop.backend.tool;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class DatabaseUtil {

    private static Dialect dialect = new Oracle10gDialect();

    private DatabaseUtil() {
    }

    public static Dialect getDialect() {
        return dialect;
    }
}
