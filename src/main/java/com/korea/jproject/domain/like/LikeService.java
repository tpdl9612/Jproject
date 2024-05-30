package com.korea.jproject.domain.like;

import com.korea.jproject.domain.article.Article;
import com.korea.jproject.domain.article.ArticleRepository;
import com.korea.jproject.domain.article.ArticleService;
import com.korea.jproject.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    public void addLike(Long articleId, Member member){
        Article article = articleService.getArticle(articleId);
        if(!likeRepository.existsByMemberAndArticle(member, article)){
            article.setLikeCount(article.getLikeCount() + 1);
            likeRepository.save(new Like(member, article));
        }
        else {
            article.setLikeCount(article.getLikeCount() - 1);
            likeRepository.deleteByMemberAndArticle(member, article);
        }


    }
}
