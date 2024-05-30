package com.korea.jproject.domain.like;

import com.korea.jproject.domain.article.Article;
import com.korea.jproject.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByMemberAndArticle(Member member, Article article);

    void deleteByMemberAndArticle(Member member, Article article);
}
