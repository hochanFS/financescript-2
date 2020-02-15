package com.financescript.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscellaneousController {
    @GetMapping("/termsOfUse")
    public String termsOfUse() {

        return "/misc/termsOfUse";
    }
}
