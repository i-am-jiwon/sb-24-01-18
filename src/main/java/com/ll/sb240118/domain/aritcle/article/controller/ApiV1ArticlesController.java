package com.ll.sb240118.domain.aritcle.article.controller;

import com.ll.sb240118.domain.aritcle.article.dto.ArticleDto;
import com.ll.sb240118.domain.aritcle.article.entity.Article;
import com.ll.sb240118.domain.aritcle.article.service.ArticleService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticlesController {

 private final ArticleService articleService;

 @Getter
 public static class GetArticleResponseBody{
     private final List<ArticleDto> items;
     private final Map pagination;
     public GetArticleResponseBody(List<Article> articles) {
         items = articles
                 .stream()
                 .map(ArticleDto::new)
                 .toList();

         pagination = Map.of("page", 1);
     }

 }

    @GetMapping("")
    public GetArticleResponseBody getArticles(){
     return new GetArticleResponseBody(articleService.findAllByOrderByIdDesc());
    }

}
