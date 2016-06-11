package com.zhytnik.shop.dto;

import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
public class FieldDto implements Dto{

    private Long id;

    @Length(min = 3, max = 30)
    private String name;
    @NotNull
    private Integer order;
    @NotNull
    private PrimitiveType primitiveType;

    private boolean required;

    public FieldDto(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
