package com.example.demo_project.diary.article;

import com.example.demo_project.diary.category.Category;
import com.example.demo_project.diary.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCategoryOrderByCreateDateDesc (Category category);
    List<Article> findByMemberAndTitleContaining(Member member, String keyword);
    List<Article> findByMemberAndContentContaining(Member member, String keyword);
    List<Article> findByMemberAndTitleContainingOrMemberAndContentContaining(Member member, String titleKeyword, Member member1, String contentKeyword);
    List<Article> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

}
