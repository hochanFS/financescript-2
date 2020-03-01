package com.financescript.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index()
    {
        return "index";
    }

    @GetMapping("/notImplemented")
    public String notImplemented() {
        return "notYetBuilt";
    }

    @GetMapping("/about")
    public String aboutUs() {
        return "aboutUs";
    }
}
