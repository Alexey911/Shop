package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.domain.historizable.IHistorizable;
import com.zhytnik.shop.domain.historizable.IHistorizableDescription;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.market.product.ProductDescription;
import com.zhytnik.shop.domain.market.product.ProductPointer;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class HistoryUtil {

    private HistoryUtil() {
    }

    public static void setUp(IHistorizable<?> historizable) {
        if (historizable instanceof Product) {
            setUpProduct((Product) historizable);
        } else {
            throw new RuntimeException();
        }
    }

    private static void setUpProduct(Product product) {
        final IHistorizableDescription<ProductPointer> desc = new ProductDescription();

        final ProductPointer pointer = new ProductPointer();
        pointer.setSlices(newHashSet(product));

        desc.setHistorizablePointer(pointer);
        desc.setValidFrom(product.getChangeDate());
        product.setHistorizableDescription(desc);
    }
}
