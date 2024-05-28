package com.korea.jproject.domain.comment;

import com.korea.jproject.domain.article.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")

public class CommentController {
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createComment(@RequestParam("articleId") Long articleId,
                                @RequestParam("comment") String commentText,
                                Principal principal) {
        commentService.createComment(commentText, articleId, principal.getName());
        return "redirect:/article/detail/" + articleId;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String showUpdateCommentPage(@PathVariable("id") Long commentId, Principal principal, Model model) {
        CommentResponseDto commentResponseDto = commentService.getCommentResponseDto(commentId);
        if (!commentResponseDto.getNickname().equals(principal.getName())) {
            return "redirect:/article/detail/" + commentResponseDto.getArticleId();
        }
        model.addAttribute("comment", commentResponseDto);
        return "detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable("id") Long commentId,
                                @RequestParam("comment") String newCommentText,
                                Principal principal) {
        commentService.updateComment(commentId, newCommentText, principal.getName());
        CommentResponseDto commentResponseDto = commentService.getCommentResponseDto(commentId);
        return "redirect:/article/detail/" + commentResponseDto.getArticleId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Long commentId, Principal principal) {
        Comment comment = commentService.getComment(commentId); // 새로 추가된 getComment 메소드 필요
        Long articleId = comment.getArticle().getId();
        commentService.deleteComment(commentId, principal.getName());
        return "redirect:/article/detail/" + articleId;
    }
}
