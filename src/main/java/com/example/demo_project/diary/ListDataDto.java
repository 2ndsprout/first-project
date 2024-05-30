package com.example.demo_project.diary;

import com.example.demo_project.diary.article.Article;
import com.example.demo_project.diary.category.Category;
import com.example.demo_project.diary.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListDataDto {

    List<Category> categoryList;
    Category targetCategory;
    List<Article> articleList;
    Article targetArticle;
    List<Article> searchedArticleList;
    Member member;
}
