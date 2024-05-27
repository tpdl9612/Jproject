package com.korea.jproject.domain.article;

import com.korea.jproject.domain.member.Member;
import com.korea.jproject.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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
    public String list(Model model, @PageableDefault(size = 10, sort="id", direction= Sort.Direction.DESC) Pageable pageable){
        Page<Article> articleList = articleService.getArticleList(pageable);
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
        return "write_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/write")
    public String write(@RequestParam("title") String title, @RequestParam("content") String content,
                        Principal principal){
        Member author = memberService.getMember(principal.getName());
        articleService.create(title, content, author);
        return "redirect:/article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/update/{id}")
    public String update(Model model, @PathVariable("id")Long id, Principal principal){
        Article article = articleService.getArticle(id);
        if(!article.getAuthor().getLoginId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        model.addAttribute("article", article);
        return "article_form";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/update/{id}")
    public String update(@PathVariable("id")Long id, Principal principal, @RequestParam("title")String title,
                         @RequestParam("content")String content){
        Article article = articleService.getArticle(id);
        if(!article.getAuthor().getLoginId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.articleService.update(article, title, content);
        return "redirect:/article/detail/%d".formatted(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/delete/{id}")
    public String delete(@PathVariable("id")Long id, Principal principal){
        Article article = articleService.getArticle(id);
        if(!article.getAuthor().getLoginId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.articleService.delete(id);
        return "redirect:/article/list";
    }
}
