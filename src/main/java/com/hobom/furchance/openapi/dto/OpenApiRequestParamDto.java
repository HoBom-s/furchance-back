package com.hobom.furchance.openapi.dto;

import com.hobom.furchance.openapi.constant.OpenApiConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiRequestParamDto {

    private String serviceKey;

    private final String bgnde = OpenApiConstant.SEARCH_BEGIN_DATE;

    private final String endde = OpenApiConstant.SEARCH_END_DATE;

    private final String upr_cd = OpenApiConstant.SEOUL_CODE;

    private final String numOfRows = OpenApiConstant.COUNT;

    private final String _type = OpenApiConstant.TYPE_JSON;

}
