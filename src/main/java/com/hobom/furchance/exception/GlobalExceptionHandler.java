package com.hobom.furchance.exception;

import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.exception.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException customException) {

        ApiResponse<Void> customExceptionResponse = ApiResponse.of(customException.getStatus(), customException.getMessage());

        return new ResponseEntity<>(customExceptionResponse, customException.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {

        ApiResponse<Void> exceptionResponse = ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_ERROR + exception.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
