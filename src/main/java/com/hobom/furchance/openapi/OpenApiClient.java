package com.hobom.furchance.openapi;

import com.hobom.furchance.constant.ApiConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "openApi", url = ApiConstant.OPENAPI_BASE_URL)
public interface OpenApiClient {

    @GetMapping(ApiConstant.PARAM_ABANDONED_ANIMAL)
    public Map getAbandonedAnimals(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("bgnde") String bgnde,
            @RequestParam("endde") String endde,
            @RequestParam("upr_cd") String upr_cd,
            @RequestParam("numOfRows") String numOfRows,
            @RequestParam("_type") String _type
    );
}
