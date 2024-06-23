package com.hobom.furchance.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hobom.furchance.constant.OpenApiConstant;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(Url.OpenApiUrl.BASE_URL)
public class OpenApiController {

    @Autowired
    OpenApiClient openApiClient;

    @Value("${openapi.authKey.d}")
    public String OPENAPI_AUTHKEY_D;

    @GetMapping
    public Map getAbandonedAnimals() throws JsonProcessingException {
        return Util.CustomParser.parseStringToMap(openApiClient.getAbandonedAnimals(
                OPENAPI_AUTHKEY_D
                , OpenApiConstant.SEARCH_BEGIN_DATE
                , OpenApiConstant.SEARCH_END_DATE
                , OpenApiConstant.SEOUL_CODE
                , OpenApiConstant.COUNT
                , OpenApiConstant.TYPE_JSON));
    }

}
