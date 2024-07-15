package com.hobom.furchance.article.repository;

import com.hobom.furchance.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
