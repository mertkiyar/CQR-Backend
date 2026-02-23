package com.mrtkyr.classqroom.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    // 1xxx - Record / Data Errors
    NO_RECORD_EXIST("1001", "Record not found!"),
    RECORD_ALREADY_EXIST("1002", "Record already exists!"),
    INVALID_ID("1003", "Invalid ID supplied!"),

    // 2xxx - Validation Errors
    VALIDATION_ERROR("2001", "Validation failed!"),
    MISSING_REQUIRED_FIELD("2002", "Required field is missing!"),
    INVALID_FORMAT("2003", "Invalid format!"),
    INVALID_EMAIL("2004", "Invalid email address!"),
    INVALID_PASSWORD("2005", "Invalid password!"),

    // 3xxx - Authentication / Authorization
    UNAUTHORIZED("3001", "Unauthorized access!"),
    FORBIDDEN("3002", "Access denied!"),
    INVALID_TOKEN("3003", "Invalid or expired token!"),
    LOGIN_FAILED("3004", "Username or password is incorrect!"),

    // 4xxx - Business Logic Errors
    BUSINESS_RULE_VIOLATION("4001", "Business rule violation!"),
    OPERATION_NOT_ALLOWED("4002", "Operation not allowed!"),

    // 5xxx - System Errors
    DATABASE_ERROR("5001", "Database error occurred!"),
    EXTERNAL_SERVICE_ERROR("5002", "External service error!"),

    // 9999 - Unexpected Error
    GENERAL_EXCEPTION("9999", "An unexpected error occurred.");

    private final String code;
    private final String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}