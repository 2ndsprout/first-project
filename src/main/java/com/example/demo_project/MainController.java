package com.example.demo_project;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String main(Principal principal, Model model) {
        try {
            ListDataDto listDataDto = this.mainService.getDefaultListData(principal);
            model.addAttribute("listDataDto", listDataDto);
            return "main";
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return "redirect:/";
        }

    }
}
