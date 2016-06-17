package com.zhytnik.shop.util.dataset;

import com.zhytnik.shop.util.dataset.DropTable.Phase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.springframework.test.context.TestContext;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import static com.zhytnik.shop.util.dataset.DataBaseCleaner.clear;
import static com.zhytnik.shop.util.dataset.DataSetLoader.*;
import static com.zhytnik.shop.util.dataset.DropTable.Phase.AFTER;
import static com.zhytnik.shop.util.dataset.DropTable.Phase.BEFORE;
import static org.dbunit.operation.DatabaseOperation.INSERT;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
public class DataSetUtil {

    private static String schema;

    public static void verify(TestContext context) throws Exception {
        if (getExpectedDataSet(context) != null) {
            new DataSetComparator().compare(loadActualDataSet(context), loadExpectedDataSet(context));
        }
    }

    public static boolean hasDataSet(TestContext context) {
        return (getDefaultDataSet(context) != null || hasCustomDataSet(context)) && !hasClearSchema(context);
    }

    private static boolean hasClearSchema(TestContext context) {
        return context.getTestMethod().getAnnotation(ClearSchema.class) != null;
    }

    static ExpectedDataSet getExpectedDataSet(TestContext context) {
        final Method method = context.getTestMethod();
        return method.getAnnotation(ExpectedDataSet.class);
    }

    static DataSet getDefaultDataSet(TestContext context) {
        final Class<?> testClass = context.getTestClass();
        return testClass.getAnnotation(DataSet.class);
    }

    static boolean hasCustomDataSet(TestContext context) {
        return getCustomDataSet(context) != null;
    }

    static DataSet getCustomDataSet(TestContext context) {
        final Method method = context.getTestMethod();
        return method.getAnnotation(DataSet.class);
    }

    public static void installDataSet(TestContext context) throws Exception {
        final IDatabaseConnection connection = getConnection(context);
        final IDataSet dataSet = loadDataSet(context);
        clearSchema(context);
        INSERT.execute(connection, dataSet);
        connection.close();
    }

    public static void clearSchema(TestContext context) throws Exception {
        clear(getConnection(context));
    }

    public static void dropTablesBeforeTest(TestContext context) {
        final DropTable drop = getDropTable(context);
        if (hasPhase(drop, BEFORE)) dropTables(context, drop.tables());
    }

    public static void dropTablesAfterTest(TestContext context) {
        final DropTable drop = getDropTable(context);
        if (hasPhase(drop, AFTER)) dropTables(context, drop.tables());
    }

    private static boolean hasPhase(DropTable drop, Phase phase) {
        for (Phase aPhase : drop.phases()) {
            if (aPhase.equals(phase)) return true;
        }
        return false;
    }

    public static boolean hasDrops(TestContext context) {
        return context.getTestMethod().getAnnotation(DropTable.class) != null;
    }

    private static DropTable getDropTable(TestContext context) {
        return context.getTestMethod().getAnnotation(DropTable.class);
    }

    private static void dropTables(TestContext context, String[] tables) {
        try {
            final IDatabaseConnection connection = getConnection(context);
            for (String table : tables) {
                DataBaseCleaner.drop(connection, table);
            }
        } catch (SQLException | DatabaseUnitException e) {
            throw new RuntimeException(e);
        }
    }

    static IDatabaseConnection getConnection(TestContext context) throws SQLException, DatabaseUnitException {
        final DataSource dataSource = context.getApplicationContext().getBean(DataSource.class);
        final Connection connection = dataSource.getConnection();
        return new DatabaseConnection(connection, schema);
    }

    public static void setSchema(String schema) {
        DataSetUtil.schema = schema;
    }
}
