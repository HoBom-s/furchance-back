package com.hobom.furchance.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String getTodayDate() {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return FORMATTER.format(now);
    }

    public static class CustomParser {

        public static JsonNode parseStringToJson(String stringJson) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readTree(stringJson);
        }

    }

}
