package com.zhytnik.shop.backend.tool;

import com.google.gson.JsonObject;
import com.zhytnik.shop.backend.access.DynamicAccessUtil;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.market.product.Product;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 19.06.2016
 */
class ProductFastJsonConverter {

    public String convert(Product product) {
        final JsonObject json = new JsonObject();
        final List<DynamicField> fields = product.getDynamicType().getFields();
        for (int i = 0, size = fields.size(); i < size; i++) {
            final Object field = DynamicAccessUtil.getDynamicValue(product, i);
            final String value = field != null ? field.toString() : "null";
            json.addProperty(fields.get(i).getName(), value);
        }
        return json.toString();
    }
}
