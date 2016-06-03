package com.zhytnik.shop.domain.market;

import com.zhytnik.shop.domain.BasicEntity;
import com.zhytnik.shop.domain.text.MultilanguageString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_SELLER")
public class Seller extends BasicEntity {

    @OneToOne
    private MultilanguageString name;

    public MultilanguageString getName() {
        return name;
    }

    public void setName(MultilanguageString name) {
        this.name = name;
    }
}
