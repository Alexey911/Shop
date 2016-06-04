package com.zhytnik.shop.backend.dao.search;

import com.zhytnik.shop.domain.dynamic.DynamicField;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexey Zhytnik
 * @since 03.06.2016
 */
public class Filter {

    private List<DynamicField> fields = newArrayList();
    private List<Relation> relations = newArrayList();
    private List<Object[]> arguments = newArrayList();

    public Filter add(DynamicField field, Relation relation) {
        fields.add(field);
        arguments.add(null);
        relations.add(relation);
        return this;
    }

    public Filter add(DynamicField field, Relation relation, Object... args) {
        fields.add(field);
        arguments.add(args);
        relations.add(relation);
        return this;
    }

    public boolean isEmpty() {
        return fields.isEmpty();
    }

    public List<DynamicField> getFields() {
        return fields;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public List<Object[]> getArguments() {
        return arguments;
    }
}
