package com.korea.jproject.domain.article;

import com.korea.jproject.domain.member.Member;
import com.korea.jproject.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/")
    public String root(){
        return "redirect:/article/list";
    }

    @GetMapping("/article/list")
    public String list(Model model){
        List<Article> articleList = articleService.getArticleList();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }

    @GetMapping("/article/detail/{id}")
    public String detail(Model model, @PathVariable("id")Long id){
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        return "detail";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/write")
    public String write(){
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/write")
    public String write(@RequestParam("title") String title, @RequestParam("content") String content,
                        Principal principal){
        Member author = memberService.getMember(principal.getName());
        articleService.create(title, content, author);
        return "redirect:/article/list";
    }
}
