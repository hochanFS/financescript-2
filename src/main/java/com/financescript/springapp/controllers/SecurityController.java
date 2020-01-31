package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.dto.PasswordDto;
import com.financescript.springapp.services.EmailService;
import com.financescript.springapp.services.MemberService;
import com.financescript.springapp.services.PasswordTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@Slf4j
public class SecurityController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final PasswordTokenService passwordTokenService;
    private final String SIGN_UP_PAGE = "security/sign-up";
    private final String SIGN_IN_PAGE = "security/login";
    private final String HOME_PAGE = "index";
    private final String FOROGT_PASSWORD = "security/forgotPassword";

    @Autowired
    public SecurityController(MemberService memberService, EmailService emailService, PasswordTokenService passwordTokenService) {
        this.memberService = memberService;
        this.emailService = emailService;
        this.passwordTokenService = passwordTokenService;
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
        String username = memberDto.getUsername();
        log.info("Processing sign-up form for: " + username);

        if (bindingResult.hasErrors()) {
            return SIGN_UP_PAGE;
        }
        Member memberMatchedByUsername = memberService.findByUsername(memberDto.getUsername());
        if (memberMatchedByUsername != null) {
            model.addAttribute("signUpError", "The username already exists.");
            return SIGN_UP_PAGE;
        }
        Member memberMatchedByEmail = memberService.findByEmail(memberDto.getEmail());
        if (memberMatchedByEmail != null) {
            model.addAttribute("signUpError", "The email is already registered.");
            return SIGN_UP_PAGE;
        }

        memberService.save(memberDto);
        return HOME_PAGE;
    }

    @RequestMapping(value = "/login")
    public String signInForm() {
        return SIGN_IN_PAGE;
    }

    @GetMapping(value = "/forgotPassword")
    public String forgotPassword(Model model) {
        model.addAttribute("success", "false");
        return FOROGT_PASSWORD;
    }

    @PostMapping(value = "/resetPassword")
    public String resetPassword(HttpServletRequest request, @RequestParam("email") String email, Model model) {
        if (email == null) {
            model.addAttribute("success", "false");
            return FOROGT_PASSWORD;
        }
        Member member = memberService.findByEmail(email);
        if (member == null) {
            model.addAttribute("success", "false");
            model.addAttribute("noUserFoundError",
                    "We were unable to find the account associated with the email.");
            return FOROGT_PASSWORD;
        }
        String token = UUID.randomUUID().toString();
        String appUrl = request.getRequestURL().toString();
        memberService.createPasswordResetTokenForUser(member, token);
        emailService.sendSimpleMessage(email, "FinanceScript: Reset Password", writeEmail(appUrl, member, token));
        model.addAttribute("success", "true");

        return FOROGT_PASSWORD;
    }

    @GetMapping(value = "/resetPassword/user/changePassword")
    public String readToken(Model model,
                                         @RequestParam("id") Long id, @RequestParam("token") String token) {
        String result = passwordTokenService.validatePasswordToken(id, token);
        if (result != null) {
            model.addAttribute("message", result);
            return "redirect:/login";
        }
        return "redirect:/updatePassword";
    }

    private String writeEmail(String appUrl, Member member, String token) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hi ").append(member.getUsername()).append("\n\n")
                .append("We have received a request from this email address \"")
                .append(member.getEmail()).append("\"")
                .append(" to reset the password on FinanceScript.\n\n")
                .append("Your username is: ").append(member.getUsername())
                .append("\n\nTo reset your password, please go to: ")
                .append(appUrl).append("/user/changePassword?id=").append(member.getId())
                .append("&token=").append(token)
                .append("\n\nIf you did not initiate such a request from us, please ignore this email.");
        return sb.toString();
    }

    @GetMapping(value = "/updatePassword")
    public String showChangePasswordPage(Model model) {
        model.addAttribute("passwordDto", new PasswordDto());
        return "security/changePassword";
    }

    @PostMapping(value = "/updatePassword")
    public String updatePassword(@Valid @ModelAttribute("passwordDto") PasswordDto passwordDto, BindingResult bindingResult, Model model) {
        Member user =
                (Member) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        String username = user.getUsername();
        memberService.changePassword(username, passwordDto);
        if (bindingResult.hasErrors()) {
            model.addAttribute("success", "false");
            return "redirect:/updatePassword";
        }
        else {
            model.addAttribute("success", "true");
            return "redirect:/login";
        }

    }


}
