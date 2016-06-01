package com.zhytnik.shop.domain.dynamic;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_COLUMN_TYPE")
public class ColumnType extends DomainObject {

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION")
    private Integer order;

    @Column(name = "TYPE")
    private PrimitiveType type;

    @Column(name = "REQUIRED")
    private boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
