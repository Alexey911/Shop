package com.zhytnik.shop.util;

import com.zhytnik.shop.testing.DataSet;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.reflect.Method;

import static com.zhytnik.shop.util.DataSetUtil.installDataSet;
import static com.zhytnik.shop.util.TransactionalTest.prepareUncheckedTestContext;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
class MethodExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(TestContext context) throws Exception {
        prepareUncheckedTestContext(context);
    }

    @Override
    public void beforeTestClass(TestContext context) throws Exception {
        final Class<?> testClass = context.getTestClass();
        final DataSet dataSet = testClass.getAnnotation(DataSet.class);
        if (dataSet != null) {
            installDataSet(context, dataSet);
        }
    }

    @Override
    public void beforeTestMethod(TestContext context) throws Exception {
        final Method method = context.getTestMethod();
        final DataSet dataSet = method.getAnnotation(DataSet.class);
        if (dataSet != null) {
            installDataSet(context, method, dataSet);
        }
    }
}
