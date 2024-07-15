package com.hobom.furchance.article.dto;

import com.hobom.furchance.article.entity.Article;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.entity.User;
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
