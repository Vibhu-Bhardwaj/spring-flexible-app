package com.example.app.exception;

import com.example.app.config.CorrelationFilter;
import com.example.app.dto.ApiResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException ex) {

        return ApiResponse.builder()
                .success(false)
                .message("Validation failed: " + ex.getBindingResult().getFieldError().getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .correlationId(MDC.get(CorrelationFilter.CORRELATION_ID))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleGenericException(Exception ex) {

        return ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .correlationId(MDC.get(CorrelationFilter.CORRELATION_ID))
                .build();
    }
}