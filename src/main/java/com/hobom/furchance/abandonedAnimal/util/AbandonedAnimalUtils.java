package com.hobom.furchance.abandonedAnimal.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AbandonedAnimalUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String getTodayDate() {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return FORMATTER.format(now);
    }

    public static class CustomParser {

        public static JsonNode parseStringToJson(String stringJson){
            try{
                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                return objectMapper.readTree(stringJson);
            } catch (JsonProcessingException jsonProcessingException) {
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.JSON_ERROR + jsonProcessingException.getMessage());
            }
        }
    }
}
