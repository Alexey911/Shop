package com.zhytnik.shop.domain.dynamic;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.*;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_DYNAMIC_TYPE")
public class DynamicType extends DomainObject {

    public static final String DYNAMIC_ID_FIELD = "ID";

    @Column(name = "NAME", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ColumnType> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnType> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnType> columns) {
        this.columns = columns;
    }
}
