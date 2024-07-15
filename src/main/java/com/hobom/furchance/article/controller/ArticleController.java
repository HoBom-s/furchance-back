package com.hobom.furchance.article.controller;

import com.hobom.furchance.article.dto.ArticlePaginationRequestParamDto;
import com.hobom.furchance.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.article.dto.ArticleResponseDto;
import com.hobom.furchance.article.dto.ArticleUpdateRequestDto;
import com.hobom.furchance.article.service.ArticleService;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.util.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Url.Article.BASE_URL)
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(Url.ID_PARAM)
    private ResponseEntity<ApiResponse<ArticleResponseDto>> getOneArticleById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: get one article by Id", articleService.getOneArticleById(id)));
    }

    @GetMapping(Url.PAGINATION)
    private ResponseEntity<ApiResponse<Page<ArticleResponseDto>>> getArticlePagination(@ModelAttribute @Valid ArticlePaginationRequestParamDto articlePaginationRequestParamDto) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: article pagination", articleService.getArticlePagination(articlePaginationRequestParamDto)));
    }

    @PostMapping
    private ResponseEntity<ApiResponse<ArticleResponseDto>> createOneArticle(@RequestBody @Valid ArticleCreateRequestDto articleCreateRequestDto, HttpServletRequest request) {

        Long userId = CommonUtils.getVerifiedUserId(request);

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: create one article", articleService.createOneArticle(userId, articleCreateRequestDto)));
    }

    @PatchMapping(Url.ID_PARAM)
    private ResponseEntity<ApiResponse<ArticleResponseDto>> updateOneArticle(@PathVariable("id") Long id, @RequestBody ArticleUpdateRequestDto articleUpdateRequestDto, HttpServletRequest request) {

        Long userId = CommonUtils.getVerifiedUserId(request);

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one article", articleService.updateOneArticle(id, userId, articleUpdateRequestDto)));
    }

    @DeleteMapping(Url.ID_PARAM)
    private ResponseEntity<ApiResponse<ArticleResponseDto>> deleteOneArticle(@PathVariable("id") Long id, HttpServletRequest request) {

        Long userId = CommonUtils.getVerifiedUserId(request);

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: remove one article", articleService.removeOneArticle(id, userId)));
    }
}
