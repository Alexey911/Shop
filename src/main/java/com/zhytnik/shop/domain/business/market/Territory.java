package com.zhytnik.shop.domain.business.market;

import com.zhytnik.shop.domain.DomainObject;
import com.zhytnik.shop.domain.text.MultilanguageString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_TERRITORY")
public class Territory extends DomainObject {

    @OneToOne
    private MultilanguageString name;

    @OneToOne
    private MultilanguageString description;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
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

    public MultilanguageString getDescription() {
        return description;
    }

    public void setDescription(MultilanguageString description) {
        this.description = description;
    }
}
