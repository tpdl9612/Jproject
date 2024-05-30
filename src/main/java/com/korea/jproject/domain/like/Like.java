package com.korea.jproject.domain.like;

import com.korea.jproject.domain.article.Article;
import com.korea.jproject.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "likes")
@Getter
@Setter
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Article article;

    public Like(Member member, Article article){
        this.member = member;
        this.article = article;
    }
}
