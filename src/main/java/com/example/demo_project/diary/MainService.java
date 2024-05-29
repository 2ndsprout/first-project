package com.example.demo_project.diary;

import com.example.demo_project.diary.article.Article;
import com.example.demo_project.diary.article.ArticleService;
import com.example.demo_project.diary.category.Category;
import com.example.demo_project.diary.category.CategoryService;
import com.example.demo_project.diary.member.Member;
import com.example.demo_project.diary.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ArticleService articleService;

    public Member getMember(String username) {
        return this.memberService.getMember(username);
    }

    public List<Category> getCategoryListByMember(Member member) {
        return this.categoryService.getCategoryListByMember(member);
    }

    public MainDataDto getMainDataDefault(Principal principal) {

        Member member = this.getMember(principal.getName());
        List<Category> categoryList = this.getCategoryListByMember(member);
        if (categoryList.isEmpty()) {
            Category category = new Category();
            category.setName("new category");
            category.setContent("unknown");
            category.setMember(member);
            category.setCreateDate(LocalDateTime.now());
            category.setImgUrl("https://img.freepik.com/free-vector/note-paper-background-with-hole-punches_78370-2344.jpg?t=st=1716899988~exp=1716903588~hmac=fc80740f553db0d52574670556a5ceec88475e883dee92b9a6da818583435ba6&w=1380");
            Article article = this.articleService.saveDefault();
            category.addToArticle(article);
            this.categoryService.save(category);
        }

        MainDataDto mainDataDto = new MainDataDto(categoryList);
        return mainDataDto;
    }

    public Category getCategory (Long categoryId) {
        return this.categoryService.getCategory(categoryId);
    }

    public Category create (String name, String content, String imgUrl, Member member) {
        if (name.trim().isEmpty()) {
            name = "new Category";
        }
        if (content.trim().isEmpty()) {
            content = "";
        }
        if (imgUrl.trim().isEmpty()) {
            imgUrl = "https://img.freepik.com/free-vector/note-paper-background-with-hole-punches_78370-2344.jpg?t=st=1716899988~exp=1716903588~hmac=fc80740f553db0d52574670556a5ceec88475e883dee92b9a6da818583435ba6&w=1380";
        }
        Category category = new Category();
        category.setImgUrl(imgUrl);
        category.setName(name);
        category.setContent(content);
        category.setMember(member);
        category.setCreateDate(LocalDateTime.now());
        Article article = this.articleService.saveDefault();
        category.addToArticle(article);
        return this.categoryService.save(category);
    }
}
