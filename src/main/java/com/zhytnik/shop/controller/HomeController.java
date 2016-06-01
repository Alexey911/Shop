package com.zhytnik.shop.controller;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.domain.business.market.Product;
import com.zhytnik.shop.domain.dynamic.ColumnType;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;

@Controller
public class HomeController {

    @Autowired
    private ProductService service;

    @RequestMapping("/home")
    public ModelAndView home() {
        ColumnType column = new ColumnType();
        column.setName("my_field");
        column.setRequired(true);
        column.setType(PrimitiveType.LONG);
        column.setOrder(1);

        ColumnType c = new ColumnType();
        c.setName("ID");
        c.setRequired(true);
        c.setType(PrimitiveType.LONG);
        c.setOrder(0);

        DynamicType type = new DynamicType();
        type.setName("my_type");

        type.setColumns(newArrayList(c, column));

        service.create(type);

        Product p = new Product();
        p.setDynamicFieldsValues(new Object[2]);
        p.setDynamicType(type);

        DynamicAccessor accessor = p.getDynamicAccessor();
        accessor.set("my_field", 5);
        accessor.set(DYNAMIC_ID_FIELD, p.getId());

        service.save(p);
        service.load(3L);

        return new ModelAndView("welcome", "message", new Date());
    }
}
