package com.zhytnik.shop.backend;

import com.zhytnik.shop.domain.dynamic.ColumnType;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.exeception.InfrastructureException;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
public class TableScriptGenerator {

    private static Map<PrimitiveType, Integer> TYPE_MAPPING;

    private Dialect dialect;

    static {
        TYPE_MAPPING = newHashMap();
        TYPE_MAPPING.put(PrimitiveType.INTEGER, Types.INTEGER);
        TYPE_MAPPING.put(PrimitiveType.STRING, Types.CHAR);
        TYPE_MAPPING.put(PrimitiveType.DOUBLE, Types.DOUBLE);
        TYPE_MAPPING.put(PrimitiveType.BOOLEAN, Types.BOOLEAN);
        TYPE_MAPPING.put(PrimitiveType.DATE, Types.DATE);
    }

    public String generate(String tableName, List<ColumnType> columnTypes) {
        final Table table = new Table(tableName);

        table.addColumn(generateIdColumn());

        for (ColumnType columnType : columnTypes) {
            final Column column = createColumn(columnType);
            table.addColumn(column);
        }

        return generateScript(table, dialect);
    }

    private Column generateIdColumn() {
        final Column column = new Column("ID");
        column.setSqlType(dialect.getTypeName(Types.INTEGER));
        column.setUnique(true);
        column.setNullable(false);
        return column;
    }

    private Column createColumn(ColumnType columnType) {
        final Column column = new Column(columnType.getName());
        column.setSqlType(getSqlType(columnType.getType()));
        column.setNullable(!columnType.isRequired());
        return column;
    }

    private String getSqlType(PrimitiveType type) {
        Integer code = TYPE_MAPPING.get(type);
        if (code == null) failOnUnknownType(type);
        return dialect.getTypeName(code);
    }

    private String failOnUnknownType(PrimitiveType type) {
        throw new InfrastructureException(format("Unmapped type \"%s\"", type));
    }

    private String generateScript(Table table, Dialect dialect) {
        return table.sqlCreateString(dialect, null, null, null);
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
