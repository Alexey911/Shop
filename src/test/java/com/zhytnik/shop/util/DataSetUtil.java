package com.zhytnik.shop.util;

import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.zhytnik.shop.testing.DataSet;
import com.zhytnik.shop.testing.ExpectedDataSet;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.test.context.TestContext;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.String.format;
import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
class DataSetUtil {

    static final String END_DATASET = "-dataset.xml";
    static final String END_EXPECTED_DATASET = "-expected.xml";

    private DataSetUtil() {
    }

    static void verify(TestContext context) throws Exception {
        final Method method = context.getTestMethod();
        final ExpectedDataSet expected = method.getAnnotation(ExpectedDataSet.class);
        if (expected != null) {
            final IDataSet expectedDataSet = extractDataSetByMethod(context, method, expected);
            final IDataSet actualDataSet = getConnection(context).createDataSet();

            new DataSetComparator().compare(actualDataSet, expectedDataSet);
        }
    }

    static void installDataSet(TestContext context, DataSet dataSet) throws Exception {
        installDataSet(context, extractDataSetByClass(context, dataSet));
    }

    static void installDataSet(TestContext context, Method method, DataSet dataSet) throws Exception {
        installDataSet(context, extractDataSetByMethod(context, method, dataSet));
    }

    private static IDataSet extractDataSetByMethod(TestContext context, Method method,
                                                   DataSet dataSet) throws Exception {
        return extractDataSetByMethod(context, method, END_DATASET, dataSet.value());
    }

    private static IDataSet extractDataSetByMethod(TestContext context, Method method,
                                                   ExpectedDataSet expected) throws Exception {
        return extractDataSetByMethod(context, method, END_EXPECTED_DATASET, expected.value());
    }

    private static IDataSet extractDataSetByClass(TestContext context, DataSet dataSet) throws Exception {
        final String defaultName = context.getTestClass().getSimpleName();
        final String path;
        if (dataSet.value().isEmpty()) {
            path = defaultName + END_DATASET;
        } else {
            path = dataSet.value() + END_DATASET;
        }
        return loadDataSet(context.getTestClass(), path);
    }

    private static IDataSet extractDataSetByMethod(TestContext context, Method method,
                                                   String end, String value) throws Exception {
        final Class<?> clazz = context.getTestClass();
        final String defaultName = clazz.getSimpleName() + "." + method.getName();
        final String path;
        if (value.isEmpty()) {
            path = defaultName + end;
        } else {
            path = clazz.getSimpleName() + "." + value + end;
        }
        return loadDataSet(context.getTestClass(), path);
    }

    private static IDataSet loadDataSet(Class<?> clazz, String path) throws Exception {
        final FlatXmlDataSetLoader loader = new FlatXmlDataSetLoader();
        final IDataSet dataSet = loader.loadDataSet(clazz, path);
        if (dataSet == null) failOnNotFound(path);
        return dataSet;
    }

    private static void failOnNotFound(String dataSetName) {
        throw new RuntimeException(format("There is no dataset with \"%s\" name", dataSetName));
    }

    private static void installDataSet(TestContext context, IDataSet dataSet) throws Exception {
        final IDatabaseConnection connection = getConnection(context);
        final DatabaseOperation operation = CLEAN_INSERT;
        operation.execute(connection, dataSet);
        connection.close();
    }

    private static IDatabaseConnection getConnection(TestContext context) throws SQLException, DatabaseUnitException {
        final DataSource dataSource = context.getApplicationContext().getBean(DataSource.class);
        final Connection connection = dataSource.getConnection();
        return new DatabaseConnection(connection);
    }
}
