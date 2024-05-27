package com.korea.jproject.domain;

import com.korea.jproject.domain.article.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDataDto {
    List<Article> articleList = new ArrayList<>();

    List<Article> searchedArticleList = new ArrayList<>();

    Article article;
}
