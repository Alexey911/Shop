package com.zhytnik.shop.domain.historizable;

import com.zhytnik.shop.domain.IBasicEntity;

import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public interface IHistorizableObjectPointer<T extends IHistorizable> extends IBasicEntity {

    Set<T> getSlices();

    void setSlices(Set<T> slices);
}
