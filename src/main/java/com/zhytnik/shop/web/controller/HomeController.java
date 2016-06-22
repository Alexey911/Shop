package com.zhytnik.shop.web.controller;

import com.zhytnik.shop.util.System;
import com.zhytnik.shop.util.SystemManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HomeController {

    @RequestMapping({"/home", "/index", "/"})
    public String home() {
        return "home";
    }

    @ResponseBody
    @RequestMapping("/system")
    public System loadSystem() {
        return new SystemManager().getSystemInfo();
    }
}
