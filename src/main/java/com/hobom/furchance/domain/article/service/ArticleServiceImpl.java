package com.hobom.furchance.domain.article.service;

import com.hobom.furchance.domain.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.domain.article.dto.ArticlePaginationRequestParamDto;
import com.hobom.furchance.domain.article.dto.ArticleResponseDto;
import com.hobom.furchance.domain.article.dto.ArticleUpdateRequestDto;
import com.hobom.furchance.domain.article.entity.Article;
import com.hobom.furchance.domain.article.repository.ArticleRepository;
import com.hobom.furchance.domain.user.service.UserValidationService;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import com.hobom.furchance.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserValidationService userValidationService;

    @Override
    public ArticleResponseDto getOneArticleById(Long id) {

        Article foundArticle = articleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND + id));

        return ArticleResponseDto.from(foundArticle);
    }

    @Override
    public Page<ArticleResponseDto> getArticlePagination(ArticlePaginationRequestParamDto articlePaginationRequestParamDto) {

        int pageNum = articlePaginationRequestParamDto.getPageNum() - 1;
        int perPage = articlePaginationRequestParamDto.getPerPage();
        Sort.Direction sortDirection = Sort.Direction.fromString(String.valueOf(articlePaginationRequestParamDto.getSort()));

        Pageable pageable = PageRequest.of(pageNum, perPage, sortDirection, articlePaginationRequestParamDto.getSortField());

        return articleRepository.findAllByDeletedFalse(pageable).map(ArticleResponseDto::from);
    }

    @Override
    @Transactional
    public ArticleResponseDto createOneArticle(Long userId, ArticleCreateRequestDto articleCreateRequestDto) {

        User writer = userValidationService.findOneUserById(userId);

        Article createdArticle = articleRepository.save(Article.of(articleCreateRequestDto.getTitle(), articleCreateRequestDto.getContents(), writer));

        return ArticleResponseDto.from(createdArticle);
    }

    @Override
    @Transactional
    public ArticleResponseDto updateOneArticle(Long id, Long userId, ArticleUpdateRequestDto articleUpdateRequestDto) {

        Article foundArticle = articleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND + id));

        validateArticleWriter(userId, foundArticle);

        foundArticle.setTitle(articleUpdateRequestDto.getTitle());
        foundArticle.setContents(articleUpdateRequestDto.getContents());

        Article updatedArticle = articleRepository.save(foundArticle);

        return ArticleResponseDto.from(updatedArticle);
    }

    @Override
    @Transactional
    public ArticleResponseDto removeOneArticle(Long id, Long userId) {

        Article foundArticle = articleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND + id));

        validateArticleWriter(userId, foundArticle);

        if(foundArticle.isDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorMessage.ALREADY_DELETED + id);
        }

        foundArticle.setDeleted(true);

        Article removedArticle = articleRepository.save(foundArticle);

        return ArticleResponseDto.from(removedArticle);
    }

    private void validateArticleWriter(Long userId, Article foundArticle) {

        Long writerId = foundArticle.getUser().getId();

        userValidationService.validateUser(userId, writerId);
    }
}
