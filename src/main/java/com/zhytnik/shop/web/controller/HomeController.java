package com.zhytnik.shop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
class HomeController {

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("welcome", "message", new Date());
    }
}
