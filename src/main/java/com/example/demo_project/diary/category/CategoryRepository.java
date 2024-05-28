package com.example.demo_project.diary.category;

import com.example.demo_project.diary.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByMember (Member member);
}
