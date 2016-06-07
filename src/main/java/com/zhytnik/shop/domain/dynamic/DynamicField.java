package com.zhytnik.shop.domain.dynamic;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.*;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_FIELD")
public class DynamicField extends DomainObject {

    @Column(name = "NAME")
    private String name;

    @Column(name = "F_ORDER")
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(name = "F_TYPE")
    private PrimitiveType primitiveType;

    @Column(name = "REQUIRED")
    private boolean required;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TYPE_ID")
    private DynamicType type;

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

    public PrimitiveType getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(PrimitiveType type) {
        this.primitiveType = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setType(DynamicType type) {
        this.type = type;
    }

    public DynamicType getType() {
        return type;
    }
}
