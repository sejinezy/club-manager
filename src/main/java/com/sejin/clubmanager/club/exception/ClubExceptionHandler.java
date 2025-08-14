package com.sejin.clubmanager.club.exception;

import com.sejin.clubmanager.common.Api;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.sejin.clubmanager.club")
@Order(1)
public class ClubExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Api<Object>> handleNotFound(
            EntityNotFoundException e
    ) {
        log.warn("Club not found: {}", e.getMessage());

        Api<Object> response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .resultMessage(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Api<Object>> handleIllegalState(
            IllegalStateException e
    ) {
        log.warn("Club conflict: {}", e.getMessage());

        Api<Object> response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.CONFLICT.value()))
                .resultMessage(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

}
