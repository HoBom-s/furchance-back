package com.hobom.furchance.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCreateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

}
