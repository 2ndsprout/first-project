package com.example.demo_project.diary.article;

import com.example.demo_project.diary.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCategoryOrderByCreateDateDesc (Category category);
}
