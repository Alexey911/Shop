package com.zhytnik.shop.util;

import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.zhytnik.shop.testing.DataSet;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

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
class BeforeExecutionListener extends AbstractTestExecutionListener {

    private static final String DEFAULT_DATASET_NAME = "-dataset.xml";

    @Override
    public void beforeTestClass(TestContext context) throws Exception {
        final Class<?> testClass = context.getTestClass();
        final DataSet dataSet = testClass.getAnnotation(DataSet.class);
        if (dataSet != null) {
            installDataSet(context, extractDataSet(testClass.getSimpleName(), dataSet));
        }
    }

    @Override
    public void beforeTestMethod(TestContext context) throws Exception {
        final Method method = context.getTestMethod();
        final DataSet dataSet = method.getAnnotation(DataSet.class);
        if (dataSet != null) {
            final String name = context.getTestClass().getCanonicalName() + "." + method.getName();
            installDataSet(context, extractDataSet(name, dataSet));
        }
    }

    private String extractDataSet(String name, DataSet dataSet) {
        final String value;
        if (dataSet.value().isEmpty()) {
            value = name + DEFAULT_DATASET_NAME;
        } else {
            value = dataSet.value();
        }
        return value;
    }

    private void installDataSet(TestContext context, String path) throws Exception {
        final IDataSet dataSet = loadDataSet(context.getTestClass(), path);
        if (dataSet == null) failOnNotFound(path);

        final IDatabaseConnection connection = getConnection(context);
        final DatabaseOperation operation = CLEAN_INSERT;
        operation.execute(connection, dataSet);
    }

    private IDataSet loadDataSet(Class<?> clazz, String path) throws Exception {
        final FlatXmlDataSetLoader loader = new FlatXmlDataSetLoader();
        return loader.loadDataSet(clazz, path);
    }

    private IDataSet failOnNotFound(String dataSetName) {
        throw new RuntimeException(format("There is no dataset with \"%s\" name", dataSetName));
    }

    private IDatabaseConnection getConnection(TestContext context) throws SQLException, DatabaseUnitException {
        final DataSource dataSource = context.getApplicationContext().getBean(DataSource.class);
        final Connection connection = dataSource.getConnection();
        return new DatabaseConnection(connection);
    }
}
