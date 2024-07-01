package com.hobom.furchance.abandonedAnimal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
public class PaginationRequestParamDto {

    @NotNull(message = "Pagination page number required.")
    public int pageNum;

    @NotNull(message = "Pagination size per page required.")
    public int perPage;

    public String startDate;

    public String endDate;

    public String kind;

    @ColumnDefault("DESC")
    public Sorting sort;
}
