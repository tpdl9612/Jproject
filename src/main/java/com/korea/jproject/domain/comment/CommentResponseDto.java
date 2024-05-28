package com.korea.jproject.domain.comment;

import com.korea.jproject.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String comment;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String nickname;
    private Member author;
    private Long articleId;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.author = comment.getMember();
        this.nickname = comment.getMember().getNickname();
        this.articleId = comment.getArticle().getId();
    }

}
