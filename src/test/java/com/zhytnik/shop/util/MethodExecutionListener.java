package com.zhytnik.shop.util;

import com.zhytnik.shop.testing.DataSet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.reflect.Method;

import static com.zhytnik.shop.util.DataSetUtil.*;
import static com.zhytnik.shop.util.TransactionalTest.setTestContext;
import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
class MethodExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(TestContext context) throws Exception {
        setTestContext(context);
    }

    @Override
    public void beforeTestClass(TestContext context) throws Exception {
        final Class<?> testClass = context.getTestClass();
        final DataSet dataSet = testClass.getAnnotation(DataSet.class);
        if (dataSet != null) {
            installDataSet(context, extractDataSetByClass(context, dataSet));
        }
    }

    @Override
    public void beforeTestMethod(TestContext context) throws Exception {
        final Method method = context.getTestMethod();
        final DataSet dataSet = method.getAnnotation(DataSet.class);
        if (dataSet != null) {
            installDataSet(context, extractDataSetByMethod(context, method, dataSet));
        }
    }

    private void installDataSet(TestContext context, IDataSet dataSet) throws Exception {
        final IDatabaseConnection connection = getConnection(context);
        final DatabaseOperation operation = CLEAN_INSERT;
        operation.execute(connection, dataSet);
        connection.close();
    }
}
