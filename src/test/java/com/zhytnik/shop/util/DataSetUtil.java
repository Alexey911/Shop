package com.zhytnik.shop.util;

import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.zhytnik.shop.testing.DataSet;
import com.zhytnik.shop.testing.ExpectedDataSet;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.springframework.test.context.TestContext;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
class DataSetUtil {

    static final String END_DATASET = "-dataset.xml";
    static final String END_EXPECTED_DATASET = "-expected.xml";

    private DataSetUtil() {
    }

    static IDatabaseConnection getConnection(TestContext context) throws SQLException, DatabaseUnitException {
        final DataSource dataSource = context.getApplicationContext().getBean(DataSource.class);
        final Connection connection = dataSource.getConnection();
        return new DatabaseConnection(connection);
    }

    static IDataSet extractDataSetByClass(TestContext context, DataSet dataSet) throws Exception {
        final String defaultName = context.getTestClass().getSimpleName();
        final String value;
        if (dataSet.value().isEmpty()) {
            value = defaultName + END_DATASET;
        } else {
            value = dataSet.value() + END_DATASET;
        }
        return extract(context, value);
    }

    static IDataSet extractDataSetByMethod(TestContext context, Method method,
                                           DataSet dataSet) throws Exception {
        return extractDataSetByMethod(context, method, END_DATASET, dataSet.value());
    }

    static IDataSet extractDataSetByMethod(TestContext context, Method method,
                                           ExpectedDataSet expected) throws Exception {
        return extractDataSetByMethod(context, method, END_EXPECTED_DATASET, expected.value());
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
        return extract(context, path);
    }

    private static IDataSet extract(TestContext context, String path) throws Exception {
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
}
