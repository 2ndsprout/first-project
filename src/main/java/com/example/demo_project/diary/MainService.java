package com.example.demo_project.diary;

import com.example.demo_project.diary.category.Category;
import com.example.demo_project.diary.category.CategoryService;
import com.example.demo_project.diary.member.Member;
import com.example.demo_project.diary.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MemberService memberService;
    private final CategoryService categoryService;

    public Member getMember (String username) {
        return this.memberService.getMember(username);
    }

    public List<Category> getCategoryListByMember (Member member) {
        return this.categoryService.getCategoryListByMember(member);
    }

    public MainDataDto getMainDataDefault (Principal principal) {

        Member member = this.getMember(principal.getName());
        List<Category> categoryList = this.getCategoryListByMember(member);
        if (categoryList.isEmpty()) {
            this.categoryService.create("new category", "blabla..", member);
        }

        MainDataDto mainDataDto = new MainDataDto(categoryList);
        return mainDataDto;
    }
}
