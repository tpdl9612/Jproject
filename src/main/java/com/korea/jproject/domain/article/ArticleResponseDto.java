package com.korea.jproject.domain.article;

import com.korea.jproject.domain.comment.CommentResponseDto;
import com.korea.jproject.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private Long memberId;
    private Member author;
    private int viewCount;
    private List<CommentResponseDto> comments;
    private int likeCount;

    public ArticleResponseDto(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createDate = article.getCreateDate();
        this.author = article.getAuthor();
        this.viewCount = article.getViewCount();
        this.memberId = article.getAuthor().getId();
        this.comments = article.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.likeCount = article.getLikeCount();
    }

}
