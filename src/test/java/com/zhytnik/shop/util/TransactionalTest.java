package com.zhytnik.shop.util;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.zhytnik.shop.testing.ExpectedDataSet;
import org.dbunit.dataset.IDataSet;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

import static com.zhytnik.shop.App.APP_SETTINGS;
import static com.zhytnik.shop.util.DataSetUtil.extractDataSetByMethod;
import static com.zhytnik.shop.util.DataSetUtil.getConnection;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(APP_SETTINGS)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextBeforeModesTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        MethodExecutionListener.class,
        DbUnitTestExecutionListener.class})
public abstract class TransactionalTest {

    private static ThreadLocal<TestContext> testContext = new ThreadLocal<>();

    @AfterTransaction
    public void afterTransaction() throws Exception {
        final TestContext context = testContext.get();
        testContext.remove();
        verify(context);
    }

    private void verify(TestContext context) throws Exception {
        final Method method = context.getTestMethod();
        final ExpectedDataSet expected = method.getAnnotation(ExpectedDataSet.class);
        if (expected != null) {
            final IDataSet expectedDataSet = extractDataSetByMethod(context, method, expected);
            final IDataSet actualDataSet = getConnection(context).createDataSet();
            DataSetVerifier.verify(actualDataSet, expectedDataSet);
        }
    }

    static void setTestContext(TestContext testContext) {
        TransactionalTest.testContext.set(testContext);
    }
}
