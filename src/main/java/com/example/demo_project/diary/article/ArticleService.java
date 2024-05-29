package com.example.demo_project.diary.article;

import com.example.demo_project.diary.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getArticleList (Category category) {
        return this.articleRepository.findByCategoryOrderByCreateDateDesc(category);
    }

    public Article saveDefault () {
        Article article = new Article();
        article.setTitle("new title");
        article.setContent("empty");
        article.setCreateDate(LocalDateTime.now());
        return this.articleRepository.save(article);
    }
    public Article getArticle (Long id) {
        return this.articleRepository.findById(id).orElseThrow();
    }

    public Article update (Long id, String title, String content) {
        Article article = this.getArticle(id);
        if (title.trim().isEmpty()) {
            title = "new title";
        }
        if (content.trim().isEmpty()) {
            content = "empty";
        }
        article.setTitle(title);
        article.setContent(content);
        return this.articleRepository.save(article);
    }
}
