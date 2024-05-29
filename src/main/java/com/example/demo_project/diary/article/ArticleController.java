package com.example.demo_project.diary.article;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category/{categoryId}/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final MainService mainService;

    @GetMapping("/list")
    public String list (@PathVariable("categoryId")Long categoryId, Model model, Principal principal) {
        Category category = this.mainService.getCategory(categoryId);
        Long articleId = category.getArticleList().getFirst().getId();
        ListDataDto listDataDto = this.mainService.getListData(categoryId,articleId, principal);

        model.addAttribute("listDataDto", listDataDto);

        return "list";
    }

    @GetMapping("/{articleId}")
    public String detail(@PathVariable("categoryId")Long categoryId,
                         @PathVariable("articleId") Long articleId, Model model, Principal principal) {

        ListDataDto listDataDto = this.mainService.getListData(categoryId,articleId, principal);

        model.addAttribute("listDataDto", listDataDto);

        return "list";
    }

    @PostMapping("/create")
    public String create (@PathVariable("categoryId")Long categoryId) {
        Category category = this.mainService.getCategory(categoryId);
        Article article = this.articleService.saveDefault();
        category.addToArticle(article);
        this.mainService.save(category);

        return "redirect:/category/%d/articles/%d".formatted(categoryId, article.getId());
    }

    @PostMapping("/{articleId}/update")
    public String update(@PathVariable("categoryId")Long categoryId,
                         @PathVariable("articleId") Long articleId, String title, String content) {
        Article article = this.articleService.update(articleId, title, content);

        return "redirect:/category/%d/articles/%d".formatted(categoryId, article.getId());
    }
}
