package com.zhytnik.shop.dto;

import com.zhytnik.shop.dto.core.Dto;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 11.06.2016
 */
public class TypeDto implements Dto {

    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private List<FieldDto> fields;

    private Date lastChange;

    public TypeDto() {
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

    public List<FieldDto> getFields() {
        return fields;
    }

    public void setFields(List<FieldDto> fields) {
        this.fields = fields;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }
}
