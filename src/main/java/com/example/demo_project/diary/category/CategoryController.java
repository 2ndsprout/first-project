package com.example.demo_project.diary.category;

import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.ParamHandler;
import com.example.demo_project.diary.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create (String name, String imgUrl, Principal principal, ParamHandler paramHandler) {

        Member member = this.mainService.getMember(principal.getName());
        Category category = this.mainService.create(name, imgUrl, member);

        return paramHandler.getRedirectUrl("/");

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{categoryId}/update")
    public String update (@PathVariable("categoryId")Long categoryId,
                          String name, String imgUrl, Principal principal, ParamHandler paramHandler) {

        this.mainService.checkRole(categoryId, principal);

        this.categoryService.update(categoryId, name, imgUrl);
        return paramHandler.getRedirectUrl("/");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{categoryId}/delete")
    public String delete (@PathVariable("categoryId")Long categoryId, ParamHandler paramHandler, Principal principal) {

        this.mainService.checkRole(categoryId, principal);

        this.categoryService.delete(categoryId);

        return paramHandler.getRedirectUrl("/");
    }
}
