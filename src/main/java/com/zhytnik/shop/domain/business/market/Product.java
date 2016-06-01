package com.zhytnik.shop.domain.business.market;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.domain.BasicEntity;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.text.MultilanguageString;

import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
//@Entity(name = "PRODUCT")
public class Product extends BasicEntity implements IDynamicEntity {

    private String shortName;

    private MultilanguageString title;

    private MultilanguageString description;

    private Category category;

    private Set<Supply> supplies;

    private Set<String> keywords;

    private Set<Comment> comments;

    private DynamicType type;

    private Object[] dynamicValues;

    private transient DynamicAccessor accessor;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public MultilanguageString getTitle() {
        return title;
    }

    public void setTitle(MultilanguageString title) {
        this.title = title;
    }

    public MultilanguageString getDescription() {
        return description;
    }

    public void setDescription(MultilanguageString description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(Set<Supply> supplies) {
        this.supplies = supplies;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public DynamicType getDynamicType() {
        return type;
    }

    @Override
    public void setDynamicType(DynamicType type) {
        this.type = type;
    }

    @Override
    public Object[] getDynamicFieldsValues() {
        return dynamicValues;
    }

    @Override
    public void setDynamicFieldsValues(Object[] values) {
        dynamicValues = values;
    }

    public DynamicAccessor getAccessor() {
        return accessor;
    }

    public void setAccessor(DynamicAccessor accessor) {
        this.accessor = accessor;
    }
}
