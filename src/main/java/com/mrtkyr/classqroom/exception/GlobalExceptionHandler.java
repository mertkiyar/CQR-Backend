package com.mrtkyr.classqroom.exception;

import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RootEntity<Void>> handleBadCredentials(BadCredentialsException exception) {
        return error(HttpStatus.UNAUTHORIZED, MessageType.LOGIN_FAILED, "Invalid e-mail or password");
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<RootEntity<Void>> handleBaseException(BaseException exception) {
        MessageType type = exception.getMessageType();
        HttpStatus status = switch (type) {
            case NO_RECORD_EXIST, NO_ACTIVE_SESSION -> HttpStatus.NOT_FOUND;
            case RECORD_ALREADY_EXIST -> HttpStatus.CONFLICT;
            case UNAUTHORIZED, INVALID_TOKEN, LOGIN_FAILED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
        return error(status, type, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RootEntity<Void>> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining("; "));
        return error(HttpStatus.BAD_REQUEST, MessageType.VALIDATION_ERROR, message);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<RootEntity<Void>> handleInvalidInput(java.lang.Exception exception) {
        return error(HttpStatus.BAD_REQUEST, MessageType.INVALID_FORMAT, "Invalid request format or value");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RootEntity<Void>> handleDataConflict(DataIntegrityViolationException exception) {
        log.warn("Database constraint rejected a request", exception);
        return error(HttpStatus.CONFLICT, MessageType.BUSINESS_RULE_VIOLATION,
                "Request conflicts with existing or related data");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RootEntity<Void>> handleMissingRoute(NoResourceFoundException exception) {
        return error(HttpStatus.NOT_FOUND, MessageType.NO_RECORD_EXIST, "Endpoint not found");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RootEntity<Void>> handleWrongMethod(HttpRequestMethodNotSupportedException exception) {
        return error(HttpStatus.METHOD_NOT_ALLOWED, MessageType.OPERATION_NOT_ALLOWED, "HTTP method not allowed");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<RootEntity<Void>> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException exception) {
        return error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, MessageType.INVALID_FORMAT, "Unsupported content type");
    }

    @ExceptionHandler(java.lang.Exception.class)
    public ResponseEntity<RootEntity<Void>> handleUnexpected(java.lang.Exception exception) {
        log.error("Unhandled API error", exception);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, MessageType.GENERAL_EXCEPTION,
                "An unexpected error occurred");
    }

    private ResponseEntity<RootEntity<Void>> error(HttpStatus status, MessageType type, String message) {
        return ResponseEntity.status(status).body(RootEntity.error(type.getCode(), message));
    }
}
