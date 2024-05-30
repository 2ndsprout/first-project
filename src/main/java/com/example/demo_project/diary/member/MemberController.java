package com.example.demo_project.diary.member;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.ParamHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MainService mainService;

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

    @GetMapping("/member/{username}/profile")
    public String profile(@PathVariable("username") String username,
                          Principal principal, Model model, ParamHandler paramHandler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (!currentUsername.equals(username)) {
            return paramHandler.getRedirectUrl("/");
        }
        ListDataDto listDataDto = this.mainService.getDefaultListData(principal, paramHandler.getKeyword(), paramHandler.getType());
        model.addAttribute("listDataDto", listDataDto);
        model.addAttribute("paramHandler", paramHandler);
        return "profile_form";
    }
    @PostMapping("/member/{username}/update")
    public String update (@PathVariable("username") String username,
                          String nickname, ParamHandler paramHandler) {

        this.memberService.changeNickname(username, nickname);
        return paramHandler.getRedirectUrl("/member/%s/profile".formatted(URLEncoder.encode(username, StandardCharsets.UTF_8)));
    }
}
