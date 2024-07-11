package com.hobom.furchance.article.service;

import com.hobom.furchance.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.article.dto.ArticlePaginationRequestParamDto;
import com.hobom.furchance.article.dto.ArticleResponseDto;
import com.hobom.furchance.article.dto.ArticleUpdateRequestDto;
import com.hobom.furchance.article.entity.Article;
import com.hobom.furchance.article.repository.ArticleRepository;
import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    @Override
    public ArticleResponseDto getOneArticleById(Long id) {

        Article foundArticle = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return ArticleResponseDto.from(foundArticle);
    }

    @Override
    public ArticleResponseDto[] getArticlePagination(ArticlePaginationRequestParamDto articlePaginationRequestParamDto) {
        return new ArticleResponseDto[0];
    }

    @Override
    public ArticleResponseDto createOneArticle(Long userId, ArticleCreateRequestDto articleCreateRequestDto) {

        User writer = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        Article createdArticle = articleRepository.save(new Article(articleCreateRequestDto.getTitle(), articleCreateRequestDto.getContents(), writer));

        return ArticleResponseDto.from(createdArticle);
    }

    @Override
    public ArticleResponseDto updateOneArticle(Long id, ArticleUpdateRequestDto articleUpdateRequestDto) {
        return null;
    }

    @Override
    public ArticleResponseDto removeOneArticle(Long id) {
        return null;
    }
}
