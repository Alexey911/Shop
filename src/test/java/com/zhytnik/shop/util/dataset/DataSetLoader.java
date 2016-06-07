package com.zhytnik.shop.util.dataset;

import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.springframework.test.context.TestContext;

import java.lang.reflect.Method;
import java.sql.SQLException;

import static com.zhytnik.shop.util.dataset.DataSetUtil.*;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 07.06.2016
 */
public class DataSetLoader {

    private static final String END_DATASET = "-dataset.xml";
    private static final String END_EXPECTED_DATASET = "-expected.xml";

    private DataSetLoader() {
    }

    static IDataSet loadActualDataSet(TestContext context) throws DatabaseUnitException, SQLException {
        return getConnection(context).createDataSet();
    }

    static IDataSet loadDataSet(TestContext context) throws Exception {
        final DataSet customDataSet = getCustomDataSet(context);
        if (hasCustomDataSet(context)) {
            return loadDataSetByMethod(context, customDataSet);
        }
        return loadDataSetByClass(context, getDefaultDataSet(context));
    }

    private static IDataSet loadDataSetByMethod(TestContext context, DataSet dataSet) throws Exception {
        return loadDataSetByMethodName(context, context.getTestMethod(),
                END_DATASET, dataSet.value());
    }

    static IDataSet loadExpectedDataSet(TestContext context) throws Exception {
        return loadDataSetByMethodName(context, context.getTestMethod(),
                END_EXPECTED_DATASET, getExpectedDataSet(context).value());
    }

    private static IDataSet loadDataSetByClass(TestContext context, DataSet dataSet) throws Exception {
        final String defaultName = context.getTestClass().getSimpleName();
        final String path;
        if (dataSet.value().isEmpty()) {
            path = defaultName + END_DATASET;
        } else {
            path = dataSet.value() + END_DATASET;
        }
        return loadDataSet(context.getTestClass(), path);
    }

    private static IDataSet loadDataSetByMethodName(TestContext context, Method method,
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
}
