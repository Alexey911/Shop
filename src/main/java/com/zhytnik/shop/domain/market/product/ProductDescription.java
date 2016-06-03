package com.zhytnik.shop.domain.market.product;

import com.zhytnik.shop.domain.BasicEntity;
import com.zhytnik.shop.domain.historizable.IHistorizableDescription;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author Alexey Zhytnik
 * @since 03.06.2016
 */
@Entity(name = "T_DESCRIPTION")
public class ProductDescription extends BasicEntity implements IHistorizableDescription<ProductPointer> {

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProductPointer pointer;

    @Override
    public Date getValidFrom() {
        return validFrom;
    }

    @Override
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public Date getValidTo() {
        return validTo;
    }

    @Override
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public ProductPointer getHistorizablePointer() {
        return pointer;
    }

    @Override
    public void setHistorizablePointer(ProductPointer pointer) {
        this.pointer = pointer;
    }
}
