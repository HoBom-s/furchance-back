package com.hobom.furchance.abandonedAnimal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequestParamDto {

    @NotNull
    public int pageNum;

    @NotNull
    public int perPage;

    public Sorting sort = Sorting.ASC;

    public String sortField = "noticeEdt";

    public String searchStartDate;

    public String searchEndDate;

    public String kind;

}
