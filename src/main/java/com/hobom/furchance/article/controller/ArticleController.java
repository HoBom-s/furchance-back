package com.hobom.furchance.article.controller;

import com.hobom.furchance.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.article.dto.ArticleResponseDto;
import com.hobom.furchance.article.dto.ArticleUpdateRequestDto;
import com.hobom.furchance.article.service.ArticleService;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Url.Article.BASE_URL)
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(Url.ID_PARAM)
    private ResponseEntity<ApiResponse<ArticleResponseDto>> getOneArticleById(@PathVariable("id") Long id){

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: get one article by Id", articleService.getOneArticleById(id)));
    }

    @PostMapping
    private ResponseEntity<ApiResponse<ArticleResponseDto>> createArticle(@RequestBody @Valid ArticleCreateRequestDto articleCreateRequestDto, HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("verifiedUserId");

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: create one article", articleService.createOneArticle(userId, articleCreateRequestDto)));
    }

    @PatchMapping(Url.ID_PARAM)
    private ResponseEntity<ApiResponse<ArticleResponseDto>> updateOneArticle(@PathVariable("id") Long id, @RequestBody ArticleUpdateRequestDto articleUpdateRequestDto, HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("verifiedUserId");

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one article", articleService.updateOneArticle(id, userId, articleUpdateRequestDto)));
    }
}
