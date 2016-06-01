package com.zhytnik.shop.domain.business.location;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public class Area extends Place {

    private Country country;

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}
