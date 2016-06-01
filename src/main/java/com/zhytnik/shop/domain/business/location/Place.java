package com.zhytnik.shop.domain.business.location;

import com.zhytnik.shop.domain.DomainObject;
import com.zhytnik.shop.domain.text.MultilanguageString;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public abstract class Place extends DomainObject {

    private MultilanguageString name;

    private Double latitude;

    private Double longitude;

    public MultilanguageString getName() {
        return name;
    }

    public void setName(MultilanguageString name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
