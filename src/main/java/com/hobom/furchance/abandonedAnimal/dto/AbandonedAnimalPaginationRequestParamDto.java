package com.hobom.furchance.abandonedAnimal.dto;

import com.hobom.furchance.dto.Sorting;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbandonedAnimalPaginationRequestParamDto {

    @NotNull
    private int pageNum;

    @NotNull
    private int perPage;

    private Sorting sort = Sorting.ASC;

    private String sortField = "noticeEdt";

    private String searchStartDate;

    private String searchEndDate;

    private String kind;

}
