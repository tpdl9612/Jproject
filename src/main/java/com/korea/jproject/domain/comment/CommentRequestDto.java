package com.korea.jproject.domain.comment;

import com.korea.jproject.domain.article.Article;
import com.korea.jproject.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private Long id;

    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Member member;
    private Article article;

    public Comment toEntity() {
        Comment comments = Comment.builder()
                .id(id)
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .member(member)
                .article(article)
                .build();
        return comments;
    }
}
