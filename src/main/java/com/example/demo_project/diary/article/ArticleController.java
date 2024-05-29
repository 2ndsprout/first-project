package com.example.demo_project.diary.article;

import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category/{categoryId}/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final MainService mainService;

    @GetMapping("/list")
    public String list (@PathVariable("categoryId")Long categoryId, Model model) {
        Category category = this.mainService.getCategory(categoryId);
        List<Article> articleList = this.articleService.getArticleList(category);

        model.addAttribute("category", category);
        model.addAttribute("articleList", articleList);

        return "list";
    }
}
