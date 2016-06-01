package com.zhytnik.shop.domain.dynamic;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_DYNAMIC_TYPE")
public class DynamicType extends DomainObject {

    @Column(name = "NAME", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
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
