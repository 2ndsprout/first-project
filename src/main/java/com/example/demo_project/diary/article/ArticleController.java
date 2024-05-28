package com.example.demo_project.diary.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category/{categoryId}/articles")
public class ArticleController {

    private final ArticleService articleService;
}
