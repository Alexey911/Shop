package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.product.Product;

import static com.zhytnik.shop.assertion.CommonAssert.shouldBeFoundById;

/**
 * @author Alexey Zhytnik
 * @since 23.06.2016
 */
public class ProductCreator {

    private TypeRepository typeRepository;

    public Product createByType(Long typeId) {
        return createByType(typeId, false);
    }

    public Product createByType(Long typeId, boolean withDynamic) {
        final DynamicType type = typeRepository.findOne(typeId);
        shouldBeFoundById(type, typeId);
        return createProduct(type, withDynamic);
    }

    private Product createProduct(DynamicType type, boolean withDynamic) {
        final Product product = new Product();
        product.setDynamicType(type);
        if (withDynamic) {
            product.setDynamicFieldsValues(new Object[type.getFields().size()]);
        }
        return product;
    }

    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }
}
