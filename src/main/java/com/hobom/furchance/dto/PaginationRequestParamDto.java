package com.hobom.furchance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequestParamDto {

    @NotNull
    private int pageNum;

    @NotNull
    private int perPage;
}
