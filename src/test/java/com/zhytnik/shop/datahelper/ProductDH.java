package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;

import static com.zhytnik.shop.datahelper.DynamicTypeDH.createTypeWithSingleField;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class ProductDH {

    private ProductDH() {
    }

    public static Product createProduct() {
        return createProductByType(createTypeWithSingleField());
    }

    public static Product createProductByType(DynamicType type) {
        final Product p = new Product();
        p.setId(85L);
        p.setDynamicType(type);
        p.setDynamicFieldsValues(new Object[type.getFields().size()]);
        p.setTitle(createMultiString());
        p.setDynamicType(type);
        p.setCode(88L);
        p.setShortName("product_1");
        return p;
    }

    private static MultilanguageString createMultiString() {
        final MultilanguageString title = new MultilanguageString();
        title.add(new MultilanguageTranslation());
        return title;
    }
}
