package com.zhytnik.shop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {

    @RequestMapping({"/home", "/index", "/"})
    public String home() {
        return "home";
    }
}
