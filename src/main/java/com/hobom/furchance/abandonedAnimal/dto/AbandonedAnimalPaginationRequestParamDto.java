package com.hobom.furchance.abandonedAnimal.dto;

import com.hobom.furchance.dto.PaginationRequestParamDto;
import com.hobom.furchance.dto.Sorting;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbandonedAnimalPaginationRequestParamDto extends PaginationRequestParamDto {

    private Sorting sort = Sorting.ASC;

    private String sortField = "noticeEdt";

    private String searchStartDate;

    private String searchEndDate;

    private String kind;

}
