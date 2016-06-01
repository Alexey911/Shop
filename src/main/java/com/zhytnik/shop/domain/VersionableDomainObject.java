package com.zhytnik.shop.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
@MappedSuperclass
public class VersionableDomainObject extends BasicEntity implements IVersionableEntity {

    @Version
    @Column(name = "VERSION", precision = 10)
    private Long version;

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public void setVersion(Long version) {
        this.version = version;
    }
}
