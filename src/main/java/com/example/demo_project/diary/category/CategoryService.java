package com.example.demo_project.diary.category;

import com.example.demo_project.diary.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save (Category category) {
        return this.categoryRepository.save(category);
    }

    public Category create (String name, String content, Member member) {
        if (name.trim().isEmpty()) {
            name = "new Category";
        }
        if (content.trim().isEmpty()) {
            content = "";
        }
        Category category = new Category();
        category.setName(name);
        category.setContent(content);
        category.setMember(member);
        category.setCreateDate(LocalDateTime.now());
        return this.save(category);
    }

    public List<Category> getCategoryListByMember (Member member) {
        return this.categoryRepository.findByMember(member);
    }
}
