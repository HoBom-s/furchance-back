package com.hobom.furchance.article.dto;

import com.hobom.furchance.dto.Sorting;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticlePaginationRequestParamDto {

    @NotNull
    private int pageNum;

    @NotNull
    private int perPage;

    private Sorting sort = Sorting.ASC;
}
