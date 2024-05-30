package com.example.demo_project.diary.article;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.ParamHandler;
import com.example.demo_project.diary.category.Category;
import com.example.demo_project.diary.member.Member;
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
    public String list (@PathVariable("categoryId")Long categoryId, Model model, Principal principal, ParamHandler paramHandler) {

        Category category = this.mainService.getCategory(categoryId);
        Member member = category.getMember();
        if (category.getArticleList().isEmpty()) {
            Article article = this.articleService.saveDefault(member);
            category.addToArticle(article);
            this.mainService.save(category);
            return "redirect:/category/%d/articles/list".formatted(categoryId);
        }
        Long articleId = category.getArticleList().getFirst().getId();
        ListDataDto listDataDto = this.mainService.getListData(categoryId, articleId, principal, paramHandler.getKeyword(), paramHandler.getType());
        model.addAttribute("listDataDto", listDataDto);
        model.addAttribute("paramHandler", paramHandler);
        return "list";
    }

    @GetMapping("/{articleId}")
    public String detail(@PathVariable("categoryId")Long categoryId,
                         @PathVariable("articleId") Long articleId, Model model, Principal principal, ParamHandler paramHandler) {

        ListDataDto listDataDto = this.mainService.getListData(categoryId,articleId, principal, paramHandler.getKeyword(), paramHandler.getType());

        model.addAttribute("listDataDto", listDataDto);
        model.addAttribute("paramHandler", paramHandler);
        return "list";
    }

    @PostMapping("/create")
    public String create (@PathVariable("categoryId")Long categoryId) {
        Category category = this.mainService.getCategory(categoryId);
        Member member = category.getMember();
        Article article = this.articleService.saveDefault(member);
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

    @PostMapping("/{articleId}/delete")
    public String delete(@PathVariable("categoryId")Long categoryId,
                         @PathVariable("articleId") Long articleId) {
        this.articleService.delete(articleId);

        return "redirect:/category/%d/articles/list".formatted(categoryId);
    }
}
