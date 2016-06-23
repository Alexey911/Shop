package com.zhytnik.shop.web.controller;

import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.service.ProductDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
@Controller
@RequestMapping("/products")
@SuppressWarnings("UnusedParameters")
class ProductController {

    @Autowired
    private ProductDtoService service;

    @ResponseBody
    @RequestMapping(value = "{id}", method = GET)
    public ProductDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ResponseBody
    @RequestMapping(method = POST)
    public Long create(@Valid @RequestBody ProductDto product, BindingResult result) {
        return service.create(product);
    }

    @ResponseBody
    @RequestMapping(method = GET)
    public List<ProductDto> loadAll() {
        return service.loadAll();
    }
}
