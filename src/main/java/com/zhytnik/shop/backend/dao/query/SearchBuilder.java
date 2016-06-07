package com.zhytnik.shop.backend.dao.query;

import com.zhytnik.shop.backend.dao.search.Filter;
import com.zhytnik.shop.backend.dao.search.Relation;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.exeception.InfrastructureException;
import com.zhytnik.shop.exeception.NotSupportedOperationException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.sql.Select;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.zhytnik.shop.backend.dao.query.QueryBuilder.getDefaultSelect;
import static com.zhytnik.shop.backend.dao.query.QueryUtil.setResultTransformByType;
import static com.zhytnik.shop.backend.dao.search.Relation.*;
import static com.zhytnik.shop.backend.tool.TypeUtil.getTypeConverter;
import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.*;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 03.06.2016
 */
class SearchBuilder {

    private static final Map<PrimitiveType, Set<Relation>> SUPPORTED_OPERATIONS;

    static {
        SUPPORTED_OPERATIONS = new EnumMap<>(PrimitiveType.class);
        SUPPORTED_OPERATIONS.put(BOOLEAN, newHashSet(EQUAL));
        SUPPORTED_OPERATIONS.put(DOUBLE, newHashSet(MORE, LESS, EQUAL, BETWEEN));
        SUPPORTED_OPERATIONS.put(LONG, newHashSet(MORE, LESS, EQUAL, BETWEEN));
        SUPPORTED_OPERATIONS.put(DATE, newHashSet(MORE, LESS, EQUAL, BETWEEN));
        SUPPORTED_OPERATIONS.put(CURRENCY, newHashSet(MORE, LESS, EQUAL, BETWEEN));
    }

    private SearchBuilder() {
    }

    static SQLQuery buildQuery(Session session, Filter filter, DynamicType type) {
        final Select select = prepareSelect(filter, type);
        final SQLQuery query = session.createSQLQuery(select.toStatementString());
        fillArguments(filter, query);
        setIdTransform(query);
        setResultTransformByType(query, type);
        return query;
    }

    private static Select prepareSelect(Filter filter, DynamicType type) {
        final int size = filter.getFields().size();
        final StringBuilder where = new StringBuilder(size * 15 + 10);
        if (size > 1) {
            where.append("( ");
        }
        for (int i = 0, preLast = size - 1; i < preLast; i++) {
            where.append(buildConditionAt(filter, i)).append(" OR ");
        }
        //last or single entity contains without OR-statement
        where.append(buildConditionAt(filter, size - 1));

        if (size > 1) {
            where.append(" )");
        }
        final Select select = getDefaultSelect(type, true);
        return select.setWhereClause(where.toString());
    }

    private static String buildConditionAt(Filter filter, int pos) {
        final DynamicField field = filter.getFields().get(pos);
        final PrimitiveType type = field.getPrimitiveType();
        final Relation relation = filter.getRelations().get(pos);
        checkTypeSupport(type);
        checkOperationSupport(type, relation);
        return createCondition(field.getName(), relation);
    }

    private static void checkTypeSupport(PrimitiveType type) {
        if (!SUPPORTED_OPERATIONS.containsKey(type)) {
            throw new NotSupportedOperationException(format("Not supported type \"%s\"", type));
        }
    }

    private static void checkOperationSupport(PrimitiveType type, Relation relation) {
        if (!SUPPORTED_OPERATIONS.get(type).contains(relation)) {
            throw new NotSupportedOperationException(format("Type \"%s\" not supports \"%s\"", type, relation));
        }
    }

    private static String createCondition(String property, Relation relation) {
        switch (relation) {
            case LESS:
                return createLessStatement(property);
            case MORE:
                return createMoreStatement(property);
            case EQUAL:
                return createEqualStatement(property);
            case BETWEEN:
                return createBetweenStatement(property);
        }
        throw new InfrastructureException();
    }

    private static String createLessStatement(String property) {
        return property + " < ?";
    }

    private static String createMoreStatement(String property) {
        return property + " > ?";
    }

    private static String createEqualStatement(String property) {
        return property + " = ?";
    }

    private static String createBetweenStatement(String property) {
        return property + " between ? ?";
    }

    private static void fillArguments(Filter filter, Query query) {
        int pos = 0;
        for (Object[] args : filter.getArguments()) {
            if (args != null) {
                for (Object arg : args) {
                    query.setParameter(pos++, arg);
                }
            }
        }
    }

    private static void setIdTransform(SQLQuery query) {
        query.addScalar(DYNAMIC_ID_FIELD, getTypeConverter(LONG));
    }
}
