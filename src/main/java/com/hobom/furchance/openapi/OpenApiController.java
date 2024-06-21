package com.hobom.furchance.openapi;

import com.hobom.furchance.constant.ApiConstant;
import com.hobom.furchance.url.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping(Url.OpenApiUrl.BASE_URL)
public class OpenApiController  {

    @Autowired
    OpenApiClient openApiClient;

    @Value("${openapi.authKey}")
    private static String OPENAPI_AUTHKEY;

    @GetMapping
    public String getAbandonedAnimals() {
        return openApiClient.getAbandonedAnimals(
                OPENAPI_AUTHKEY
                , ApiConstant.SEARCH_BEGIN_DATE
                , ApiConstant.SEARCH_END_DATE
                , ApiConstant.SEOUL_CODE
                , ApiConstant.COUNT
                , ApiConstant.TYPE_JSON);
    }

}
