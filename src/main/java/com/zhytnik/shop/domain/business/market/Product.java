package com.zhytnik.shop.domain.business.market;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.domain.VersionableDomainObject;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.text.MultilanguageString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_PRODUCT")
public class Product extends VersionableDomainObject implements IDynamicEntity {

    @Column(name = "SHORT_NAME", length = 30)
    private String shortName;

    @ManyToOne(cascade = CascadeType.ALL)
    private MultilanguageString title;

    @ManyToOne(cascade = CascadeType.ALL)
    private MultilanguageString description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Category category;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Supply> supplies;

    @ElementCollection
    @CollectionTable(name = "T_KEYWORDS", joinColumns = @JoinColumn(name = "T_PRODUCT_ID"))
    @Column(name = "VALUE")
    private Set<String> keywords;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Comment> comments;

    @ManyToOne(cascade = CascadeType.ALL)
    private DynamicType type;

    @Transient
    private Object[] dynamicValues;

    @Transient
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

    @Override
    public DynamicAccessor getDynamicAccessor() {
        if (accessor == null) {
            accessor = new DynamicAccessor(this);
        }
        return accessor;
    }

    @Override
    public void setDynamicAccessor(DynamicAccessor accessor) {
        this.accessor = accessor;
    }
}
