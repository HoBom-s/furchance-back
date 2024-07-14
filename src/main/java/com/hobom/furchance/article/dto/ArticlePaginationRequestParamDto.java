package com.hobom.furchance.article.dto;

import com.hobom.furchance.dto.PaginationRequestParamDto;
import com.hobom.furchance.dto.Sorting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticlePaginationRequestParamDto extends PaginationRequestParamDto {

    private Sorting sort = Sorting.DESC;

    private String sortField = "createdAt";
}
