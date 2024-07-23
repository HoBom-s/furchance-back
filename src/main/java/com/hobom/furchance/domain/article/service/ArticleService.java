package com.hobom.furchance.domain.article.service;

import com.hobom.furchance.domain.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.domain.article.dto.ArticlePaginationRequestParamDto;
import com.hobom.furchance.domain.article.dto.ArticleResponseDto;
import com.hobom.furchance.domain.article.dto.ArticleUpdateRequestDto;
import org.springframework.data.domain.Page;

public interface ArticleService {

    ArticleResponseDto getOneArticleById(Long id);

    Page<ArticleResponseDto> getArticlePagination(ArticlePaginationRequestParamDto articlePaginationRequestParamDto);

    ArticleResponseDto createOneArticle(Long userId, ArticleCreateRequestDto articleCreateRequestDto);

    ArticleResponseDto updateOneArticle(Long id, Long userId, ArticleUpdateRequestDto articleUpdateRequestDto);

    ArticleResponseDto removeOneArticle(Long id, Long userId);
}
