package com.zhytnik.shop.domain.market;

import com.zhytnik.shop.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_SUPPLY")
public class Supply extends BasicEntity {

    @ManyToOne
    private Seller seller;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Territory homeland;

    @ManyToMany
    private Set<Territory> market;

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Territory getHomeland() {
        return homeland;
    }

    public void setHomeland(Territory homeland) {
        this.homeland = homeland;
    }

    public Set<Territory> getMarket() {
        return market;
    }

    public void setMarket(Set<Territory> market) {
        this.market = market;
    }
}
