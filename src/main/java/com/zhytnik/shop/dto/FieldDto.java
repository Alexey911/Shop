package com.zhytnik.shop.dto;

import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.dto.core.Identity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
public class FieldDto implements Dto, Identity {

    private Long id;

    @Length(min = 3, max = 30)
    private String name;
    @NotNull
    private Integer order;
    @NotNull
    private PrimitiveType type;
    @NotNull
    private MultiStringDto description;

    private boolean required;

    public FieldDto() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public MultiStringDto getDescription() {
        return description;
    }

    public void setDescription(MultiStringDto description) {
        this.description = description;
    }
}
