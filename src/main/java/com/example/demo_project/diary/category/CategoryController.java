package com.example.demo_project.diary.category;

import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final MainService mainService;

    @PostMapping("/create")
    public String create (String name, String imgUrl, Principal principal ) {
        Member member = this.mainService.getMember(principal.getName());
        Category category = this.mainService.create(name, imgUrl, member);

        return "redirect:/";

    }

    @PostMapping("/{categoryId}/update")
    public String update (@PathVariable("categoryId")Long categoryId,
                          String name, String imgUrl) {
            this.categoryService.update(categoryId, name, imgUrl);
            return "redirect:/";
    }

    @PostMapping("/{categoryId}/delete")
    public String delete (@PathVariable("categoryId")Long categoryId) {
        this.categoryService.delete(categoryId);

        return "redirect:/";
    }
}
