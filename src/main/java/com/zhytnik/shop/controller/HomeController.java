package com.zhytnik.shop.controller;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.product.ProductDescription;
import com.zhytnik.shop.domain.market.product.ProductPointer;
import com.zhytnik.shop.service.ProductService;
import com.zhytnik.shop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/home")
    public ModelAndView home() {
        final DynamicField customField = new DynamicField();
        customField.setName("my_field");
        customField.setRequired(true);
        customField.setType(LONG);
        customField.setOrder(0);

        final DynamicType type = new DynamicType();
        type.setName("my_type");
        type.setFields(newArrayList(customField));

        typeService.create(type);

        final Product product = productService.createByType(type);
        ProductDescription desc = new ProductDescription();

        ProductPointer pointer = new ProductPointer();
        pointer.setSlices(Collections.singleton(product));

        desc.setHistorizablePointer(pointer);

        product.setHistorizableDescription(desc);

        DynamicAccessor accessor = product.getDynamicAccessor();
        accessor.set("my_field", 5L);

        product.setKeywords(newHashSet("a", "b"));

        productService.save(product);

//        productService.findByFilter(type, new Filter().add(customField, MORE, 4));

        productService.loadById(product.getId());

        return new ModelAndView("welcome", "message", new Date());
    }
}
