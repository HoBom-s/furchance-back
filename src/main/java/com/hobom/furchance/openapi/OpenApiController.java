package com.hobom.furchance.openapi;

import com.hobom.furchance.url.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping(Url.OpenApiUrl.BASE_URL)
public class OpenApiController {

    @Value("${openapi.authKey}")
    private static String OPENAPI_AUTHKEY;

}
