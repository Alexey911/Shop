package com.zhytnik.shop.util;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import static com.zhytnik.shop.App.CONTEXT_SETTINGS;
import static com.zhytnik.shop.util.dataset.DataSetUtil.verify;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(CONTEXT_SETTINGS)
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextBeforeModesTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        DataSetExecutionListener.class,
        ExpectedDataSetListener.class})
public abstract class TransactionalTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static ThreadLocal<TestContext> uncheckedContext = new ThreadLocal<>();

    @AfterTransaction
    public void afterTransaction() throws Exception {
        final TestContext context = uncheckedContext.get();
        uncheckedContext.remove();
        verify(context);
    }

    static void checkExpectedDataSet(TestContext testContext) {
        TransactionalTest.uncheckedContext.set(testContext);
    }
}
