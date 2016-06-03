package com.zhytnik.shop.domain.market.product;

import com.zhytnik.shop.domain.BasicEntity;
import com.zhytnik.shop.domain.historizable.IHistorizableObjectPointer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 03.06.2016
 */
@Entity(name = "T_POINTER")
public class ProductPointer extends BasicEntity implements IHistorizableObjectPointer<Product> {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Product> slices;

    @Override
    public Set<Product> getSlices() {
        return slices;
    }

    @Override
    public void setSlices(Set<Product> slices) {
        this.slices = slices;
    }
}
