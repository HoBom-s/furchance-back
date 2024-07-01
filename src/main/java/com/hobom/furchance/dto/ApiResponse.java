package com.hobom.furchance.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final int status;

    private final String message;

    private final T data;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status.value(), message, data);
    }
}
