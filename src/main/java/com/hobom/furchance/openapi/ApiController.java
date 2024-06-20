package com.hobom.furchance.openapi;

import com.hobom.furchance.constant.ApiConstant;
import com.hobom.furchance.url.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(Url.ApiUrl.BASE_URL)
public class ApiController {

    @Value("${openapi.authKey}")
    String OPENAPI_AUTHKEY;

    @GetMapping
    public String getAbandonedAnimalsInString() throws IOException {

        String urlBuilder = Url.OpenApiUrl.BASE_URL + Url.OpenApiUrl.ABANDONED_ANIMAL_PARAM+
                "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + OPENAPI_AUTHKEY +
                "&" + URLEncoder.encode("bgnde", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(ApiConstant.SEARCH_BEGIN_DATE, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("endde", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(ApiConstant.SEARCH_END_DATE, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("upr_cd", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(ApiConstant.SEOUL_CODE, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(ApiConstant.TYPE_JSON, StandardCharsets.UTF_8);
        URL url = new URL(urlBuilder);

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

        return sb.toString();
    }

}
