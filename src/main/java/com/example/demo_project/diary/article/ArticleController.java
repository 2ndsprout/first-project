package com.example.demo_project.diary.article;

import com.example.demo_project.diary.ListDataDto;
import com.example.demo_project.diary.MainService;
import com.example.demo_project.diary.ParamHandler;
import com.example.demo_project.diary.category.Category;
import com.example.demo_project.diary.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category/{categoryId}/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final MainService mainService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list (@PathVariable("categoryId")Long categoryId, Model model, Principal principal, ParamHandler paramHandler) {

        Category category = this.mainService.getCategory(categoryId);
        Member member = category.getMember();

        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the current user is the owner of the category or has ROLE_ADMIN
        boolean isOwner = member.getUsername().equals(principal.getName());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }

        if (category.getArticleList().isEmpty()) {
            Article article = this.articleService.saveDefault(member);
            category.addToArticle(article);
            this.mainService.save(category);
            return paramHandler.getRedirectUrl("/category/%d/articles/list".formatted(categoryId));
        }
        Long articleId = category.getArticleList().getFirst().getId();
        ListDataDto listDataDto = this.mainService.getListData(categoryId, articleId, principal, paramHandler.getKeyword(), paramHandler.getType());
        model.addAttribute("listDataDto", listDataDto);
        model.addAttribute("paramHandler", paramHandler);
        return "list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{articleId}")
    public String detail(@PathVariable("categoryId")Long categoryId,
                         @PathVariable("articleId") Long articleId, Model model, Principal principal, ParamHandler paramHandler) {

        this.mainService.checkRole(categoryId, principal);

        ListDataDto listDataDto = this.mainService.getListData(categoryId,articleId, principal, paramHandler.getKeyword(), paramHandler.getType());

        model.addAttribute("listDataDto", listDataDto);
        model.addAttribute("paramHandler", paramHandler);
        return "list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create (@PathVariable("categoryId")Long categoryId, ParamHandler paramHandler) {
        Category category = this.mainService.getCategory(categoryId);
        Member member = category.getMember();
        Article article = this.articleService.saveDefault(member);
        category.addToArticle(article);
        this.mainService.save(category);

        return paramHandler.getRedirectUrl("/category/%d/articles/%d".formatted(categoryId, article.getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{articleId}/update")
    public String update(@PathVariable("categoryId")Long categoryId,
                         @PathVariable("articleId") Long articleId, String title, String content, ParamHandler paramHandler) {
        Article article = this.articleService.update(articleId, title, content);

        return paramHandler.getRedirectUrl("/category/%d/articles/%d".formatted(categoryId, article.getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{articleId}/delete")
    public String delete(@PathVariable("categoryId")Long categoryId,
                         @PathVariable("articleId") Long articleId, ParamHandler paramHandler) {
        this.articleService.delete(articleId);

        return paramHandler.getRedirectUrl("/category/%d/articles/list".formatted(categoryId));
    }
}
