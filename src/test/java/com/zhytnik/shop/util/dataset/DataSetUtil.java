package com.zhytnik.shop.util.dataset;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.test.context.TestContext;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import static com.zhytnik.shop.util.dataset.DataSetLoader.*;
import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
public class DataSetUtil {

    private static String schema;

    private DataSetUtil(String schema) {
        DataSetUtil.schema = schema;
    }

    public static void verify(TestContext context) throws Exception {
        if (context != null && hasExpectedDataSet(context)) {
            new DataSetComparator().compare(loadActualDataSet(context), loadExpectedDataSet(context));
        }
    }

    public static boolean hasDataSet(TestContext context) {
        return (getDefaultDataSet(context) != null || hasCustomDataSet(context)) && !hasClearSchema(context);
    }

    private static boolean hasClearSchema(TestContext context) {
        return context.getTestMethod().getAnnotation(ClearSchema.class) != null;
    }

    public static boolean hasExpectedDataSet(TestContext context) {
        return getExpectedDataSet(context) != null;
    }

    static ExpectedDataSet getExpectedDataSet(TestContext context) {
        final Method method = context.getTestMethod();
        return method.getAnnotation(ExpectedDataSet.class);
    }

    static DataSet getDefaultDataSet(TestContext context) {
        final Class<?> testClass = context.getTestClass();
        return testClass.getAnnotation(DataSet.class);
    }

    static DataSet getCustomDataSet(TestContext context) {
        final Method method = context.getTestMethod();
        return method.getAnnotation(DataSet.class);
    }

    static boolean hasCustomDataSet(TestContext context) {
        return getCustomDataSet(context) != null;
    }

    public static void installDataSet(TestContext context) throws Exception {
        final IDatabaseConnection connection = getConnection(context);
        final DatabaseOperation operation = CLEAN_INSERT;
        operation.execute(connection, loadDataSet(context));
        connection.close();
    }

    static IDatabaseConnection getConnection(TestContext context) throws SQLException, DatabaseUnitException {
        final DataSource dataSource = context.getApplicationContext().getBean(DataSource.class);
        final Connection connection = dataSource.getConnection();
        return new DatabaseConnection(connection, schema);
    }
}
