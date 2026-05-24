package com.serasa.registerandscore.core.exception;

import com.serasa.registerandscore.core.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        var error = ErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR)
                .error("Internal server error")
                .build();

        log.error("RuntimeException", e);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e) {
        var error = ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .error("Failed to process the request")
                .detail(e.getMessage())
                .build();

        log.error("HttpMessageNotReadableException", e);
        return ResponseEntity.status(error.status()).body(error);
    }
}
