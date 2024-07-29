package com.hobom.furchance.domain.article.service;

import com.hobom.furchance.domain.article.dto.ArticleCreateRequestDto;
import com.hobom.furchance.domain.article.dto.ArticlePaginationRequestParamDto;
import com.hobom.furchance.domain.article.dto.ArticleResponseDto;
import com.hobom.furchance.domain.article.dto.ArticleUpdateRequestDto;
import com.hobom.furchance.domain.article.entity.Article;
import com.hobom.furchance.domain.article.repository.ArticleRepository;
import com.hobom.furchance.domain.user.entity.User;
import com.hobom.furchance.domain.user.service.UserValidationService;
import com.hobom.furchance.dto.Sorting;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    @DisplayName("해당 ID 값의 게시물을 가져와야한다.")
    void 게시물_하나_가져오기() {

        Long articleId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Article article = new Article();
        article.setId(articleId);
        article.setUser(user);

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        ArticleResponseDto articleResponseDto = articleService.getOneArticleById(articleId);

        assertThat(articleResponseDto).isNotNull();
        assertThat(articleResponseDto.getId()).isEqualTo(articleId);
    }

    @Test
    @DisplayName("게시물 생성 시 해당 ID 값의 게시물이 존재하지 않는 경우 에러가 발생한다.")
    void 게시물_하나_가져올때_해당_ID_값이_존재하지_않는_경우() {

        Long articleId = 1L;

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        CustomException customException = assertThrows(CustomException.class, () -> {
            articleService.getOneArticleById(articleId);
        });

        assertThat(customException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("게시물 페이지네이션을 가져온다.")
    void 게시물_페이지네이션_가져오기() {

        Long articleId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Article article = new Article();
        article.setId(articleId);
        article.setUser(user);

        ArticlePaginationRequestParamDto articlePaginationRequestParamDto = new ArticlePaginationRequestParamDto();
        articlePaginationRequestParamDto.setPageNum(1);
        articlePaginationRequestParamDto.setPerPage(10);
        articlePaginationRequestParamDto.setSort(Sorting.ASC);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, articlePaginationRequestParamDto.getSortField());

        Page<Article> page = new PageImpl<>(Collections.singletonList(article));

        when(articleRepository.findAllByDeletedFalse(pageable)).thenReturn(page);

        Page<ArticleResponseDto> articleResponseDtoPage = articleService.getArticlePagination(articlePaginationRequestParamDto);

        assertThat(articleResponseDtoPage).isNotNull();
        assertThat(articleResponseDtoPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물은 반드시 생성되어야 한다.")
    void 게시물_생성하기() {

        Long articleId = 1L;
        String title = "Test title";
        String contents = "Test contents";

        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Article article = new Article();
        article.setId(articleId);
        article.setUser(user);
        article.setTitle(title);
        article.setContents(contents);

        ArticleCreateRequestDto articleCreateRequestDto = new ArticleCreateRequestDto();
        articleCreateRequestDto.setContents(title);
        articleCreateRequestDto.setTitle(contents);

        when(userValidationService.findOneUserById(userId)).thenReturn(user);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        ArticleResponseDto articleResponseDto = articleService.createOneArticle(userId, articleCreateRequestDto);

        assertThat(articleResponseDto.getId()).isEqualTo(1L);
        assertThat(articleResponseDto.getTitle()).isEqualTo(title);
        assertThat(articleResponseDto.getContents()).isEqualTo(contents);
    }

    @Test
    @DisplayName("해당 ID 값의 게시물은 수정되어야 한다.")
    void 게시물_수정하기() {

        Long articleId = 1L;
        Long userId = 1L;
        String updatedTitle = "Updated Title";
        String updatedContents = "Updated Contents";

        ArticleUpdateRequestDto requestDto = new ArticleUpdateRequestDto();
        requestDto.setTitle(updatedTitle);
        requestDto.setContents(updatedContents);

        User user = new User();
        user.setId(userId);

        Article article = new Article();
        article.setId(articleId);
        article.setUser(user);

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);

        ArticleResponseDto response = articleService.updateOneArticle(articleId, userId, requestDto);

        assertThat(response.getTitle()).isEqualTo(updatedTitle);
        assertThat(response.getContents()).isEqualTo(updatedContents);
    }

    @Test
    @DisplayName("게시물 수정 시 해당 ID 값의 게시물이 존재하지 않는 경우 에러가 발생한다.")
    void 게시물_수정시_해당_ID_값이_존재하지_않는_경우() {

        Long articleId = 1L;
        Long userId = 1L;

        Article article = new Article();
        User user = new User();
        user.setId(userId);

        article.setId(articleId);
        article.setUser(user);

        ArticleUpdateRequestDto articleUpdateRequestDto = new ArticleUpdateRequestDto();
        articleUpdateRequestDto.setTitle("Updated title");
        articleUpdateRequestDto.setContents("Updated contents");

        when(articleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        CustomException customException = assertThrows(CustomException.class, () -> {
            articleService.updateOneArticle(articleId, userId, articleUpdateRequestDto);
        });

        assertThat(customException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(customException.getMessage()).isEqualTo(ErrorMessage.NOT_FOUND + articleId);
    }

    @Test
    @DisplayName("해당 ID 값의 게시물은 삭제되어야 한다.")
    void 게시물_삭제하기() {

        Long articleId = 1L;
        Long userId = 1L;

        Article article = new Article();
        User user = new User();
        user.setId(userId);

        article.setId(articleId);
        article.setUser(user);
        article.setDeleted(false);

        when(articleRepository.findById(any(Long.class))).thenReturn(Optional.of(article));
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        articleService.removeOneArticle(articleId, userId);

        assertThat(article.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("해당 ID값의 게시물이 이미 삭제되었을 시 에러가 발생한다.")
    void 이미_게시물이_삭제된_경우() {
        Long articleId = 1L;
        Long userId = 1L;

        Article article = new Article();
        User user = new User();
        user.setId(userId);

        article.setId(articleId);
        article.setUser(user);
        article.setDeleted(true);

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        CustomException customException = assertThrows(CustomException.class, () -> {
            articleService.removeOneArticle(articleId, userId);
        });

        assertThat(customException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(customException.getMessage()).isEqualTo(ErrorMessage.ALREADY_DELETED+articleId);
    }

    @Test
    @DisplayName("게시물 삭제 시 해당 ID 값의 게시물이 존재하지 않는 경우 에러가 발생한다.")
    void 게시물_삭제시_해당_ID_값이_존재하지_않는_경우() {

        Long articleId = 1L;
        Long userId = 1L;

        Article article = new Article();
        User user = new User();
        user.setId(userId);

        article.setId(articleId);
        article.setUser(user);

        when(articleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        CustomException customException = assertThrows(CustomException.class, () -> {
            articleService.removeOneArticle(articleId, userId);
        });

        assertThat(customException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(customException.getMessage()).isEqualTo(ErrorMessage.NOT_FOUND + articleId);

    }
}