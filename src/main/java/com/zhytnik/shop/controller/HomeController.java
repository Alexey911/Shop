package com.zhytnik.shop.controller;

import com.zhytnik.shop.service.ProductService;
import com.zhytnik.shop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("welcome", "message", new Date());
    }
}
