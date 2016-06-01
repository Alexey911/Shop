package com.zhytnik.shop.domain.business.market;

import com.zhytnik.shop.domain.BasicEntity;
import com.zhytnik.shop.domain.text.MultilanguageString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_CATEGORY")
public class Category extends BasicEntity {

    @Column(name = "SHORT_NAME", length = 30)
    private String shortName;

    @OneToOne
    private MultilanguageString name;

    @OneToOne
    private MultilanguageString description;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public MultilanguageString getName() {
        return name;
    }

    public void setName(MultilanguageString name) {
        this.name = name;
    }

    public MultilanguageString getDescription() {
        return description;
    }

    public void setDescription(MultilanguageString description) {
        this.description = description;
    }
}
