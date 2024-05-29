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
            if (name.trim().isEmpty()) {
                name = "new Category";
            }
            if (content.trim().isEmpty()) {
                content = "";
            }
            if (imgUrl.trim().isEmpty()) {
                imgUrl = "https://img.freepik.com/free-vector/note-paper-background-with-hole-punches_78370-2344.jpg?t=st=1716899988~exp=1716903588~hmac=fc80740f553db0d52574670556a5ceec88475e883dee92b9a6da818583435ba6&w=1380";
            }
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
