package com.hobom.furchance.abandonedAnimal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequestParamDto {

    @NotNull
    public int pageNum = 0;

    @NotNull
    public int perPage = 20;

    public String startDate;

    public String endDate;

    public String kind;

    public Sorting sort = Sorting.ASC;
}
