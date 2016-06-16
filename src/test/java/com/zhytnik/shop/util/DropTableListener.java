package com.zhytnik.shop.util;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import static com.zhytnik.shop.util.TransactionalTest.dropTables;
import static com.zhytnik.shop.util.dataset.DataSetUtil.dropTablesBeforeTest;
import static com.zhytnik.shop.util.dataset.DataSetUtil.hasDrops;

/**
 * @author Alexey Zhytnik
 * @since 15.06.2016
 */
class DropTableListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext context) throws Exception {
        if (hasDrops(context)) dropTablesBeforeTest(context);
    }

    @Override
    public void afterTestMethod(TestContext context) throws Exception {
        if (hasDrops(context)) dropTables(context);
    }
}
