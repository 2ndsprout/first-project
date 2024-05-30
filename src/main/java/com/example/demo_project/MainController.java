package com.example.demo_project;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.ParamHandler;
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
    public String main(Principal principal, Model model, ParamHandler paramHandler) {
        try {
            ListDataDto listDataDto = this.mainService.getDefaultListData(principal, paramHandler.getKeyword(), paramHandler.getType());
            model.addAttribute("listDataDto", listDataDto);
            model.addAttribute("paramHandler", paramHandler);
            return "main";
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return "redirect:/";
        }

    }
}
