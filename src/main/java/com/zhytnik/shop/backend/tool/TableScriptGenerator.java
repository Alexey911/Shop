package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.tool.TypeUtil;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import java.sql.Types;
import java.util.List;

import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
public class TableScriptGenerator {

    private Dialect dialect;

    public String generate(String tableName, List<DynamicField> fields) {
        final Table table = new Table(tableName);

        for (DynamicField field : fields) {
            final Column column = createColumn(field);
            table.addColumn(column);
        }
        table.addColumn(generateIdColumn());
        return generateScript(table, dialect);
    }

    private Column generateIdColumn() {
        final Column column = new Column(DYNAMIC_ID_FIELD);
        column.setSqlType(dialect.getTypeName(Types.INTEGER));
        column.setUnique(true);
        column.setNullable(false);
        return column;
    }

    private Column createColumn(DynamicField field) {
        final Column column = new Column(field.getName());
        column.setSqlType(getSqlType(field.getType()));
        column.setNullable(!field.isRequired());
        return column;
    }

    private String getSqlType(PrimitiveType type) {
        Integer code = TypeUtil.getSqlTypeCode(type);
        return dialect.getTypeName(code);
    }

    private String generateScript(Table table, Dialect dialect) {
        return table.sqlCreateString(dialect, null, null, null);
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
