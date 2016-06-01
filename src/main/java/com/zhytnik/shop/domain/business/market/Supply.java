package com.zhytnik.shop.domain.business.market;

import com.zhytnik.shop.domain.BasicEntity;
import com.zhytnik.shop.domain.business.location.Place;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public class Supply extends BasicEntity {

    private Seller seller;

    private BigDecimal price;

    private Currency currency;

    private Place homeland;

    private Set<Place> market;

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

    public Place getHomeland() {
        return homeland;
    }

    public void setHomeland(Place homeland) {
        this.homeland = homeland;
    }

    public Set<Place> getMarket() {
        return market;
    }

    public void setMarket(Set<Place> market) {
        this.market = market;
    }
}
