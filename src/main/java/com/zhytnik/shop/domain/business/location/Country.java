package com.zhytnik.shop.domain.business.location;

import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public class Country extends Place {

    private Set<Area> areas;

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }
}
