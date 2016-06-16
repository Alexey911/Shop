package com.zhytnik.shop.util.dataset;

import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(DataBaseCleaner.class);

    private static final String SELECT_TABLES = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
            "where TABLE_SCHEMA='PUBLIC'";

    private static final String SELECT_SEQUENCES = "SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES " +
            "WHERE SEQUENCE_SCHEMA='PUBLIC'";

    private static final String DISABLE_FOREIGN_KEY = "SET REFERENTIAL_INTEGRITY FALSE";
    private static final String ENABLE_FOREIGN_KEY = "SET REFERENTIAL_INTEGRITY TRUE";

    private DataBaseCleaner() {
    }

    public static void clear(IDatabaseConnection connection) throws SQLException {
        logger.info("Clearing database");
        final Connection c = connection.getConnection();
        Statement s = c.createStatement();
        s.execute(DISABLE_FOREIGN_KEY);
        clearTables(s);
        s.execute(ENABLE_FOREIGN_KEY);
        resetSequences(s);
        s.close();
    }

    public static void drop(IDatabaseConnection connection, String table) throws SQLException {
        logger.info("Drop table " + table);
        final Connection c = connection.getConnection();
        try {
            Statement s = c.createStatement();
            s.execute("DROP TABLE IF EXISTS " + table);
            s.close();
        } catch (SQLException e) {
            logger.error("Can't drop table " + table);
            throw new RuntimeException(e);
        } finally {
            c.close();
        }
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
