package com.zhytnik.shop.controller;

import com.zhytnik.shop.domain.dynamic.ColumnType;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    private ProductService service;

    @RequestMapping("/home")
    public ModelAndView home() {
        ColumnType column = new ColumnType();
        column.setName("my_field");
        column.setRequired(true);
        column.setType(PrimitiveType.DOUBLE);

        DynamicType type = new DynamicType();
        type.setName("my_type");
        type.setColumns(Collections.singletonList(column));

        service.create(type);

        return new ModelAndView("welcome", "message", new Date());
    }
}
