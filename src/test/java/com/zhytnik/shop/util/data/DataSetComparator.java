package com.zhytnik.shop.util.data;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.dbunit.dataset.Columns.getColumnDiff;

/**
 * @author Alexey Zhytnik
 * @since 06.06.2016
 */
class DataSetComparator {

    public void compare(IDataSet actualDataSet, IDataSet expectedDataSet) throws Exception {
        for (String tableName : expectedDataSet.getTableNames()) {
            ITable expectedTable = expectedDataSet.getTable(tableName);
            ITable actualTable = actualDataSet.getTable(tableName);
            assertEquals(expectedTable, actualTable);
        }
    }

    private void assertEquals(ITable expectedTable, ITable actualTable) throws DatabaseUnitException {
        final Set<String> ignoredColumns = getColumnsToIgnore(expectedTable.getTableMetaData(), actualTable.getTableMetaData());
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable,
                ignoredColumns.toArray(new String[ignoredColumns.size()]));
    }

    private Set<String> getColumnsToIgnore(ITableMetaData expectedMetaData,
                                           ITableMetaData actualMetaData) throws DataSetException {
        final Column[] notSpecifiedInExpected = getColumnDiff(expectedMetaData, actualMetaData).getActual();
        final Set<String> result = new LinkedHashSet<>();
        for (Column column : notSpecifiedInExpected) {
            result.add(column.getColumnName());
        }
        return result;
    }
}
