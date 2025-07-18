package com.example.reviewservice.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ReviewNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", ex.getMessage(),
                        "path", request.getDescription(false)
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Object> handleFeignNotFound(FeignException.NotFound ex, WebRequest request) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", "Upstream service not found: " + ex.getMessage(),
                        "path", request.getDescription(false)
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignGeneric(FeignException ex, WebRequest request) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", "Error from upstream service: " + ex.getMessage(),
                        "path", request.getDescription(false)
                ),
                HttpStatus.BAD_GATEWAY
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", "Unexpected error: " + ex.getMessage(),
                        "path", request.getDescription(false)
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
