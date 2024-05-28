package com.example.demo_project.diary.member;

import com.example.demo_project.diary.category.Category;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String nickname;

    private String email;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "member")
    private List<Category> categoryList = new ArrayList<>();
}
