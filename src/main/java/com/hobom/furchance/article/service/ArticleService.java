package com.hobom.furchance.article.service;

import com.hobom.furchance.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.article.dto.ArticlePaginationRequestParamDto;
import com.hobom.furchance.article.dto.ArticleResponseDto;
import com.hobom.furchance.article.dto.ArticleUpdateRequestDto;

public interface ArticleService {

    ArticleResponseDto getOneArticleById(Long id);

    ArticleResponseDto[] getArticlePagination(ArticlePaginationRequestParamDto paginationRequestParamDto);

    ArticleResponseDto createOneArticle(Long userId, ArticleCreateRequestDto articleCreateRequestDto);

    ArticleResponseDto updateOneArticle(Long id, Long userId, ArticleUpdateRequestDto articleUpdateRequestDto);

    ArticleResponseDto removeOneArticle(Long id);
}
