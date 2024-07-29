package com.hobom.furchance.domain.article.dto;

import com.hobom.furchance.dto.PaginationRequestParamDto;
import com.hobom.furchance.dto.Sorting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePaginationRequestParamDto extends PaginationRequestParamDto {

    private Sorting sort = Sorting.DESC;

    private final String sortField = "createdAt";
}
