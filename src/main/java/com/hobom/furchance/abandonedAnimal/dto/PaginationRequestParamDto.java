package com.hobom.furchance.abandonedAnimal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequestParamDto {
    public int pageNum;

    public int perPage;

    public String startDate;

    public String endDate;

    public String kind;

    public Sorting sort;
}
