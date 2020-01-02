package com.financescript.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(Principal principal, Model model)
    {
        if (principal == null) {
            model.addAttribute("username", null);
        } else {
            model.addAttribute("username", principal.getName());
        }
        return "index";
    }
}
