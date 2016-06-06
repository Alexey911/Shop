package com.zhytnik.shop.util;

import com.google.common.collect.Lists;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.*;
import org.dbunit.dataset.filter.IColumnFilter;

import java.util.LinkedHashSet;
import java.util.List;
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
            assertEquals(expectedTable, actualTable, Lists.<IColumnFilter>newArrayList());
        }
    }

    private void assertEquals(ITable expectedTable, ITable actualTable,
                              List<IColumnFilter> columnFilters) throws DatabaseUnitException {
        final Set<String> ignoredColumns = getColumnsToIgnore(expectedTable.getTableMetaData(),
                actualTable.getTableMetaData(), columnFilters);
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable,
                ignoredColumns.toArray(new String[ignoredColumns.size()]));
    }

    private Set<String> getColumnsToIgnore(ITableMetaData expectedMetaData, ITableMetaData actualMetaData,
                                           List<IColumnFilter> columnFilters) throws DataSetException {
        if (columnFilters.isEmpty()) {
            return getColumnsToIgnore(expectedMetaData, actualMetaData);
        }
        final Set<String> ignoredColumns = new LinkedHashSet<>();
        for (IColumnFilter filter : columnFilters) {
            final FilteredTableMetaData filteredExpectedMetaData =
                    new FilteredTableMetaData(expectedMetaData, filter);
            ignoredColumns.addAll(getColumnsToIgnore(filteredExpectedMetaData, actualMetaData));
        }
        return ignoredColumns;
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
