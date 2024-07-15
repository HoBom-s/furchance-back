package com.hobom.furchance.exception.constant;

public class ErrorMessage {

    public static final String INTERNAL_ERROR = "Unexpected error occurred: ";

    public static final String NOT_FOUND = "Entity not found with given conditions: ";

    public static final String ALREADY_DELETED = "Already deleted resource: ";

    public static final String ALREADY_EXISTS = "Requested resource already exists";

    public static final String JSON_ERROR = "Error while parsing String to Json: ";

    public static final String SCHEDULE_ERROR = "Error during Quartz Job: ";

    public static final String PERMISSION = "Only the owning user have permission";

    public static final String WRONG_PASSWORD = "Invalid password";

    // Token
    public static final String TOKEN_EXPIRED = "Token expired: Both Access & Refresh token expired. Please log in again";

    public static final String TOKEN_FLAG_ERROR = "Valid token flag needed";
}
