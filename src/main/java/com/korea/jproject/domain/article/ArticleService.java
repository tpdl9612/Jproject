package com.korea.jproject.domain.article;

import com.korea.jproject.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article create(String title, String content, Member author){
        Article article = new Article();
        article.setTitle(title != null ? title : "제목 없음");
        article.setContent(content != null ? content : "내용 없음");
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

    public Page<Article> getArticleList(Pageable pageable){
        return articleRepository.findAll(pageable);
    }

    public void update(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        if(article.getTitle() == null){
            article.setTitle("제목없음");
        }
        if(article.getContent() == null){
            article.setContent("내용없음");
        }
        this.articleRepository.save(article);
    }

    public List<Article> getSearchedArticleList(String keyword){
        return articleRepository.findByTitleContaining(keyword);
    }

//    public void checkAuthor(Long id, Principal principal){
//        Article article = getArticle(id);
//        if(!article.getAuthor().getLoginId().equals(principal.getName())){
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사용 권한이 없습니다.");
//        }
//    }
}
