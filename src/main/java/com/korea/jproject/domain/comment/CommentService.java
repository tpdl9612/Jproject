package com.korea.jproject.domain.comment;

import com.korea.jproject.domain.article.Article;
import com.korea.jproject.domain.article.ArticleRepository;
import com.korea.jproject.domain.member.Member;
import com.korea.jproject.domain.member.MemberRepository;
import com.korea.jproject.domain.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createComment(String commentText, Long articleId, String memberId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID: " + articleId));
        Member member = memberRepository.findByLoginId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        Comment comment = new Comment();
        comment.setComment(commentText);
        comment.setArticle(article);
        comment.setMember(member);
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);

    }

    @Transactional
    public void updateComment(Long commentId, String newCommentText, String memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID: " + commentId));

        if (!comment.getMember().getLoginId().equals(memberId)) {
            throw new IllegalStateException("You are not authorized to update this comment.");
        }

        comment.setComment(newCommentText);
        comment.setModifiedDate(LocalDateTime.now());
        commentRepository.save(comment);
    }
    @Transactional
    public void deleteComment(Long commentId, String memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID: " + commentId));

        if (!comment.getMember().getLoginId().equals(memberId)) {
            throw new IllegalStateException("You are not authorized to delete this comment.");
        }

        commentRepository.delete(comment);
    }


    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }

    public CommentResponseDto getCommentResponseDto(Long commentId) {
        Comment comment = getComment(commentId);
        return new CommentResponseDto(comment);
    }
}
