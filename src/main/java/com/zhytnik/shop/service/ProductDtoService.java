package com.zhytnik.shop.service;

import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;
import com.zhytnik.shop.service.support.ClientSupportManager;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
public class ProductDtoService {

    private ProductService service;
    private ClientSupportManager supportManager;

    private IDtoConverter<Product, ProductDto> dtoConverter;
    private IEntityConverter<Product, ProductDto> productConverter;

    public ProductDto findById(Long id) {
        final Product product = service.findById(id);
        prepareBeforeSend(product);
        return productConverter.convert(product);
    }

    public List<ProductDto> findByKeywords(String... keywords) {
        return convert(service.findByKeywords(keywords));
    }

    public List<ProductDto> loadAll() {
        return convert(service.loadAll());
    }

    private List<ProductDto> convert(List<Product> products) {
        final List<ProductDto> dtos = newArrayList();
        for (Product product : products) {
            prepareBeforeSend(product);
            dtos.add(productConverter.convert(product));
        }
        return dtos;
    }

    public Long create(ProductDto dto) {
        final Product product = dtoConverter.convert(dto);
        prepareBeforeSave(product);
        return service.create(product);
    }

    private void prepareBeforeSend(Product product) {
        supportManager.prepareBeforeSend(product);
        supportManager.prepareBeforeSend(product.getTitle());
        supportManager.prepareBeforeSend(product.getDescription());
    }

    private void prepareBeforeSave(Product product) {
        supportManager.prepareBeforeSave(product);
        supportManager.prepareBeforeSave(product.getTitle());
        supportManager.prepareBeforeSave(product.getDescription());
    }

    public void setClientSupportManager(ClientSupportManager clientSupportManager) {
        supportManager = clientSupportManager;
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
