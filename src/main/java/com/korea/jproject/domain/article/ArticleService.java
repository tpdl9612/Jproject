package com.korea.jproject.domain.article;

import com.korea.jproject.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article create(String title, String content, Member author){
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        article.setAuthor(author);
        return articleRepository.save(article);
    }

    public void delete(Long id){
        articleRepository.deleteById(id);
    }

    public Article getArticle(Long id){
        return articleRepository.findById(id).orElseThrow();
    }

    public List<Article> getArticleList(){
        return articleRepository.findAll();
    }
}
