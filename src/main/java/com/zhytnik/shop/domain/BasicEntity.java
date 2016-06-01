package com.zhytnik.shop.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@MappedSuperclass
public class BasicEntity extends DomainObject implements IBasicEntity, Serializable {

    @Column(name = "DELETED")
    private boolean deleteFlag;

    @Override
    public boolean getDeleteFlag() {
        return deleteFlag;
    }

    @Override
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
