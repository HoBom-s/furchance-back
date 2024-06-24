package com.hobom.furchance.openapi;

import com.hobom.furchance.constant.OpenApiConstant;
import com.hobom.furchance.dto.OpenApiRequestParamDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "openApi",
        url = OpenApiConstant.OPENAPI_BASE_URL,
        configuration = OpenApiFeignClientRetryer.class
)
public interface OpenApiClient {

    @GetMapping(OpenApiConstant.PARAM_ABANDONED_ANIMAL)
    String getOpenApiAbandonedAnimals (
           @SpringQueryMap OpenApiRequestParamDto openApiRequestParamDto
    );

}
