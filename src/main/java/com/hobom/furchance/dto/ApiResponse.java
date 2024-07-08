package com.hobom.furchance.dto;

import lombok.*;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;

    private String message;

    private T data;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status.value(), message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null);
    }
}
