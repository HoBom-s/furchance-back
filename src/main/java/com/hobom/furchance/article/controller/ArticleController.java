package com.hobom.furchance.article.controller;

import com.hobom.furchance.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.article.dto.ArticleResponseDto;
import com.hobom.furchance.article.service.ArticleService;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Url.Article.BASE_URL)
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    private ResponseEntity<ApiResponse<ArticleResponseDto>> createArticle(@RequestBody @Valid ArticleCreateRequestDto articleCreateRequestDto, HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("verifiedUserId");

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: create one article", articleService.createOneArticle(userId, articleCreateRequestDto)));
    }
}
