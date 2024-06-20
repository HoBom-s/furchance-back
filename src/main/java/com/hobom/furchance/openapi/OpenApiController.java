package com.hobom.furchance.openapi;

import com.hobom.furchance.constant.ApiConstant;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping(Url.OpenApiUrl.BASE_URL)
public class OpenApiController {

    @Value("${openapi.authKey}")
    String OPENAPI_AUTHKEY;

    @GetMapping
    public Map getAbandonedAnimals() throws IOException {

        String urlString = UriComponentsBuilder.fromHttpUrl(ApiConstant.OPENAPI_BASE_URL + ApiConstant.PARAM_ABANDONED_ANIMAL)
                .queryParam("serviceKey", OPENAPI_AUTHKEY)
                .queryParam("bgnde", ApiConstant.SEARCH_BEGIN_DATE)
                .queryParam("endde", ApiConstant.SEARCH_END_DATE)
                .queryParam("upr_cd", ApiConstant.SEOUL_CODE)
                .queryParam("_type", ApiConstant.TYPE_JSON)
                .queryParam("numOfRows", ApiConstant.COUNT)
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();

        conn.disconnect();

        System.out.println("sb = " + sb);

        return Util.Parser.parseStringToMap(sb.toString());
    }

}
