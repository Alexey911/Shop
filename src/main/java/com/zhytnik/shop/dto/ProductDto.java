package com.zhytnik.shop.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.dto.core.Identity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
public class ProductDto implements Dto, Identity {

    private Long id;

    @NotNull
    private Long type;

    @Max(50)
    @NotEmpty
    private String shortName;

    @NotNull
    private MultiStringDto title;

    private MultiStringDto description;

    private Map<String, Object> fields = newHashMap();

    public ProductDto() {
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public MultiStringDto getTitle() {
        return title;
    }

    public void setTitle(MultiStringDto title) {
        this.title = title;
    }

    public MultiStringDto getDescription() {
        return description;
    }

    public void setDescription(MultiStringDto description) {
        this.description = description;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @JsonAnyGetter
    public Map<String, Object> getDynamicFields() {
        return fields;
    }

    @JsonAnySetter
    public void setDynamicField(String name, Object value) {
        fields.put(name, value);
    }

    public void setDynamicFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
