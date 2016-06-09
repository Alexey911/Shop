package com.zhytnik.shop.domain.market.product;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.domain.VersionableEntity;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.historizable.IHistorizable;
import com.zhytnik.shop.domain.historizable.IHistorizableDescription;
import com.zhytnik.shop.domain.market.Category;
import com.zhytnik.shop.domain.market.Comment;
import com.zhytnik.shop.domain.market.Supply;
import com.zhytnik.shop.domain.text.MultilanguageString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Table(name = "T_PRODUCT")
@Entity
public class Product extends VersionableEntity implements IDynamicEntity, IHistorizable<ProductPointer> {

    @Column(name = "SHORT_NAME", length = 30)
    private String shortName;

    @ManyToOne(cascade = CascadeType.ALL)
    private MultilanguageString title;

    @ManyToOne(cascade = CascadeType.ALL)
    private MultilanguageString description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Category category;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "SUPPLIES",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUPPLY_ID")
    )
    private Set<Supply> supplies;

    @ElementCollection
    @CollectionTable(name = "KEYWORDS", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @Column(name = "KEYWORD")
    private Set<String> keywords;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "COMMENTS",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "COMMENT_ID")
    )
    private Set<Comment> comments;

    @ManyToOne
    private DynamicType type;

    @Transient
    private Object[] dynamicValues;

    @Transient
    private transient DynamicAccessor accessor;

    @Column(name = "CODE")
    private Long code;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductDescription history;

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
        resetAccessor();
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

    public void resetAccessor() {
        accessor = null;
    }

    @Override
    public Long getCode() {
        return code;
    }

    @Override
    public void setCode(Long code) {
        this.code = code;
    }

    @Override
    public IHistorizableDescription<ProductPointer> getHistorizableDescription() {
        return history;
    }

    @Override
    public void setHistorizableDescription(IHistorizableDescription<ProductPointer> description) {
        this.history = (ProductDescription) description;
    }
}
