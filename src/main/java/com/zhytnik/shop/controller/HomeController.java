package com.zhytnik.shop.controller;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.backend.dao.search.Filter;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.service.ProductService;
import com.zhytnik.shop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Locale;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.zhytnik.shop.backend.dao.search.Relation.MORE;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/home")
    public ModelAndView home() {
        final DynamicField customField = createField();
        final DynamicType type = createTypeByFields(customField);
        typeService.create(type);

        final Product product = productService.createByType(type);
        fill(product);

        productService.save(product);
        productService.findByFilter(type, new Filter().add(customField, MORE, 4));
        productService.loadById(product.getId());

        return new ModelAndView("welcome", "message", new Date());
    }

    private DynamicField createField() {
        final DynamicField customField = new DynamicField();
        customField.setName("my_field");
        customField.setRequired(true);
        customField.setType(LONG);
        customField.setOrder(0);
        return customField;
    }

    private DynamicType createTypeByFields(DynamicField... fields) {
        final DynamicType type = new DynamicType();
        type.setName("my_type");
        type.setFields(newArrayList(fields));
        return type;
    }

    private void fill(Product product) {
        final DynamicAccessor accessor = product.getDynamicAccessor();
        accessor.set("my_field", 5L);

        product.setKeywords(newHashSet("a", "b"));
        product.setCode(777L);
        product.setShortName("my_product");

        final MultilanguageString title = new MultilanguageString();
        final MultilanguageTranslation translation = new MultilanguageTranslation();
        translation.setLanguage(Locale.ENGLISH);
        translation.setTranslation("My Product");
        title.add(translation);

        product.setTitle(title);
    }
}
