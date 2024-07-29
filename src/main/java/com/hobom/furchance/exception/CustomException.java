package com.hobom.furchance.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private final HttpStatus status;

    private final String message;

    public CustomException(HttpStatus status, String message) {

        super(message);

        this.status = status;
        this.message = message;
    }
}
