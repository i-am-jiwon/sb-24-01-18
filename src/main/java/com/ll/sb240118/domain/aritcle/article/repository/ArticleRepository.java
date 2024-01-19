package com.ll.sb240118.domain.aritcle.article.repository;

import com.ll.sb240118.domain.aritcle.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
