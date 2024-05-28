package com.example.demo_project.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup (MemberForm memberForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup (@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            this.memberService.create(memberForm.getUsername(), memberForm.getPassword1(), memberForm.getNickname(),memberForm.getEmail());

        }catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed","동일한 사용자가 존재합니다.");
            e.printStackTrace();
            return "signup_form";

        }catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            e.printStackTrace();
            return "signup_form";
        }
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login () {
        return "login_form";
    }
}
