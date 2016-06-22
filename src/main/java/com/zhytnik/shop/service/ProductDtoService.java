package com.zhytnik.shop.service;

import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
public class ProductDtoService {

    private ProductService service;

    private IDtoConverter<Product, ProductDto> dtoConverter;
    private IEntityConverter<Product, ProductDto> productConverter;

    public ProductDto findById(Long id) {
        return productConverter.convert(service.findById(id));
    }

    public Long create(ProductDto dto) {
        return service.create(dtoConverter.convert(dto));
    }

    public List<ProductDto> loadAll() {
        final List<ProductDto> dtos = newArrayList();
        for(Product product : service.loadAll()){
            dtos.add(productConverter.convert(product));
        }
        return dtos;
    }

    public void setDtoConverter(IDtoConverter<Product, ProductDto> dtoConverter) {
        this.dtoConverter = dtoConverter;
    }

    public void setProductConverter(IEntityConverter<Product, ProductDto> productConverter) {
        this.productConverter = productConverter;
    }

    public void setService(ProductService service) {
        this.service = service;
    }
}
