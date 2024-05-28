package com.example.demo_project.diary.category;

import com.example.demo_project.diary.article.Article;
import com.example.demo_project.diary.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    private String name;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Article> articleList = new ArrayList<>();
}
