package com.example.demo_project;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.ParamHandler;
import com.example.demo_project.diary.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public String main(Principal principal, Model model, ParamHandler paramHandler) {

        Member member = this.mainService.getMember(principal.getName());

        try {
            ListDataDto listDataDto = this.mainService.getDefaultListData(member, paramHandler.getKeyword(), paramHandler.getType());
            model.addAttribute("listDataDto", listDataDto);
            model.addAttribute("paramHandler", paramHandler);
            return "main";
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return paramHandler.getRedirectUrl("/");
        }

    }
}
