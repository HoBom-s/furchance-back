package com.hobom.furchance.openapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${openapi.animal.url}")
    private static String OPENAPI_URL;

}
