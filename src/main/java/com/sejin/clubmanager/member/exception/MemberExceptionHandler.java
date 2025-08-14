package com.sejin.clubmanager.member.exception;

import com.sejin.clubmanager.common.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.sejin.clubmanager.member")
@Order(1)
public class MemberExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Api> handleDuplicateEmail(
            DuplicateEmailException e
    ) {
        log.warn("Duplicate email: {}", e.getMessage());

        Api<Object> response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.CONFLICT.value()))
                .resultMessage(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<Api> handleClubNotFound(
            ClubNotFoundException e
    ) {
        log.warn("Club not found (from member): {}", e.getMessage());

        Api<Object> response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .resultMessage(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }
}
