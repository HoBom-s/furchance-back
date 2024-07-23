package com.hobom.furchance.domain.article.dto;

import com.hobom.furchance.domain.article.entity.Article;
import com.hobom.furchance.domain.user.dto.UserResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {

    private Long id;

    private String title;

    private String contents;

    private UserResponseDto user;

    public static ArticleResponseDto from(Article article) {

        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContents(), UserResponseDto.from(article.getUser()));
    }
}
