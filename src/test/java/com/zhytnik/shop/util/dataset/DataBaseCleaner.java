package com.zhytnik.shop.util.dataset;

import org.dbunit.database.IDatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 09.06.2016
 */
class DataBaseCleaner {

    private static final String SELECT_TABLES = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
            "where TABLE_SCHEMA='PUBLIC'";

    private static final String SELECT_SEQUENCES = "SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES " +
            "WHERE SEQUENCE_SCHEMA='PUBLIC'";

    private static final String DISABLE_FOREIGN_KEY = "SET REFERENTIAL_INTEGRITY FALSE";
    private static final String ENABLE_FOREIGN_KEY = "SET REFERENTIAL_INTEGRITY TRUE";

    private DataBaseCleaner() {
    }

    public static void clear(IDatabaseConnection connection) throws SQLException {
        final Connection c = connection.getConnection();
        Statement s = c.createStatement();
        s.execute(DISABLE_FOREIGN_KEY);
        clearTables(s);
        s.execute(ENABLE_FOREIGN_KEY);
        resetSequences(s);
        s.close();
    }

    private static void resetSequences(Statement s) throws SQLException {
        final Set<String> sequences = new HashSet<>();
        final ResultSet rs = s.executeQuery(SELECT_SEQUENCES);
        while (rs.next()) {
            sequences.add(rs.getString(1));
        }
        rs.close();

        for (String seq : sequences) s.executeUpdate("ALTER SEQUENCE " + seq + " RESTART WITH 1");
    }

    private static void clearTables(Statement s) throws SQLException {
        final Set<String> tables = new HashSet<>();
        final ResultSet rs = s.executeQuery(SELECT_TABLES);

        while (rs.next()) tables.add(rs.getString(1));
        rs.close();

        for (String table : tables) s.executeUpdate("DELETE " + table);
    }
}
