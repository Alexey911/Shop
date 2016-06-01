package com.zhytnik.shop.domain.business.market;

import com.zhytnik.shop.domain.BasicEntity;
import com.zhytnik.shop.domain.text.MultilanguageString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_CURRENCY")
public class Currency extends BasicEntity {

    @OneToOne
    private MultilanguageString name;

    @Column(name = "REDUCTION")
    private String reduction;

    @Column(name = "VAL")
    private BigDecimal value;

    public MultilanguageString getName() {
        return name;
    }

    public void setName(MultilanguageString name) {
        this.name = name;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
