package com.example.demo_project.diary.category;

import com.example.demo_project.diary.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save (Category category) {
        return this.categoryRepository.save(category);
    }

    public List<Category> getCategoryListByMember (Member member) {
        return this.categoryRepository.findByMember(member);
    }

    public void update (Long id, String name, String content, String imgUrl) {
        Optional<Category> _category = this.categoryRepository.findById(id);
        if (_category.isEmpty()) {
            throw new RuntimeException("Category Not Found");
        }
        else {
            Category category = _category.get();
            category.setName(name);
            category.setContent(content);
            category.setImgUrl(imgUrl);
            this.save(category);
        }
    }

    public void delete (Long id) {
        this.categoryRepository.deleteById(id);
    }

    public Category getCategory (Long categoryId) {
        return this.categoryRepository.findById(categoryId).orElseThrow();
    }
}
