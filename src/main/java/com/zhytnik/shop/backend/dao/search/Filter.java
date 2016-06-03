package com.zhytnik.shop.backend.dao.search;

import com.zhytnik.shop.domain.dynamic.DynamicField;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexey Zhytnik
 * @since 03.06.2016
 */
public class Filter {

    protected List<DynamicField> fields = newArrayList();
    protected List<Relation> relations = newArrayList();
    protected List<Object[]> arguments = newArrayList();

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

    public boolean isEmpty(){
        return fields.isEmpty();
    }
}
