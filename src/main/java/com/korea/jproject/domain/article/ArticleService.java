package com.korea.jproject.domain.article;

import com.korea.jproject.domain.comment.CommentRepository;
import com.korea.jproject.domain.comment.CommentResponseDto;
import com.korea.jproject.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

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

    private void increaseViewCount(Article article){
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);
    }

    public ArticleResponseDto getArticleResponseDto(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid article ID: " + id ));
        increaseViewCount(article);
        return new ArticleResponseDto(article);
    }

    public Page<ArticleResponseDto> paging(Pageable pageable){
        int page = pageable.getPageNumber() -1;
        int pageLimit = 10;

        Page<Article> articleList = articleRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<ArticleResponseDto> articleResponseDtos = articleList.map(
                articlePage -> new ArticleResponseDto(articlePage));

        return articleResponseDtos;
    }



}
