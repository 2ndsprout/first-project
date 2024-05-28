package com.example.demo_project;

import com.example.demo_project.diary.MainDataDto;
import com.example.demo_project.diary.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String main(Principal principal, Model model) {
        MainDataDto mainDataDto = this.mainService.getMainDataDefault(principal);
        model.addAttribute("mainDataDto", mainDataDto);
        return "main";
    }
}
