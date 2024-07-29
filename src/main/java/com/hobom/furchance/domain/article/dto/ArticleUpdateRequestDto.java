package com.hobom.furchance.domain.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleUpdateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

}
