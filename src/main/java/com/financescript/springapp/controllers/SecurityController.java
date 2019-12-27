package com.financescript.springapp.controllers;

import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
public class SecurityController {

    private final MemberService memberService;
    private final String SIGN_UP_PAGE = "security/sign-up";
    private final String SIGN_IN_PAGE = "security/login";
    private final String HOME_PAGE = "index";

    @Autowired
    public SecurityController(MemberService memberService) {
        this.memberService = memberService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping(value = "/sign-up")
    public String registerMemberAccount(Model model) {
        MemberDto memberDto = new MemberDto();
        model.addAttribute("member", memberDto);
        return SIGN_UP_PAGE;
    }

    @PostMapping(value="/register")
    public String processSignUpForm(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return SIGN_UP_PAGE;
        }
        memberService.save(memberDto);
        return HOME_PAGE;
    }

    @RequestMapping(value = "/login")
    public String signInForm() {
        return "security/login";
    }
}
