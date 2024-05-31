package com.example.demo_project.diary.member;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.ParamHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member/{username}/profile")
    public String profile(@PathVariable("username") String username,
                          Principal principal, Model model, ParamHandler paramHandler) {

        Member member = this.memberService.getMember(username);
        Long categoryId = member.getCategoryList().getLast().getId();

        this.mainService.checkRole(categoryId, principal);
        ListDataDto listDataDto = this.mainService.getDefaultListData(principal, paramHandler.getKeyword(), paramHandler.getType());
        model.addAttribute("listDataDto", listDataDto);
        model.addAttribute("paramHandler", paramHandler);
        return "profile_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/member/{username}/update")
    public String update (@PathVariable("username") String username,
                          String nickname, ParamHandler paramHandler) {

        this.memberService.changeNickname(username, nickname);
        return paramHandler.getRedirectUrl("/member/%s/profile".formatted(URLEncoder.encode(username, StandardCharsets.UTF_8)));
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/manage")
    public String adminPage (Model model, ParamHandler paramHandler) {
        List<Member> memberList = this.memberService.findAllMembers();
        ListDataDto listDataDto = this.mainService.getAdminData(paramHandler.getKeyword());

        model.addAttribute("memberList", memberList);
        model.addAttribute("listDataDto", listDataDto);

        return "admin_page";
    }



}
