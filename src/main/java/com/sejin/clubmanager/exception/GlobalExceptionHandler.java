package com.sejin.clubmanager.exception;

import com.sejin.clubmanager.common.Api;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    /**
     * 잘못된 JSON 형식, 날짜 포맷 불일치, 타입 변환 실패 등
     * ex) @RequestBody 파싱 실패
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Api<Object>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e
    ){
        log.warn("HttpMessageNotReadable: {}", e.getMessage());

        Api<Object> response = Api.<Object>builder()
                .resultCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .resultMessage("요청 본문을 읽을 수 없습니다. JSON 형식과 데이터 타입을 확인하세요.")
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * URL 쿼리 파라미터, PathVariable 타입 변환 실패
     * ex) Long 자리에 문자열 전달 등
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Api<Object>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.warn("Type mismatch: param={}, value={}, targetType={}",
                e.getName(), e.getValue(), e.getRequiredType());

        Api<Object> response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .resultMessage("파라미터 타입이 올바르지 않습니다. 필드: " + e.getName())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * 도메인 Advice에서 못 잡은 조회 없음(404)의 글로벌 안전망
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Api<Object>> handleEntityNotFound(
            EntityNotFoundException e,
            HttpServletRequest req) {

        log.warn("EntityNotFound: path={}, msg={}", req.getRequestURI(), e.getMessage());

        String message = (e.getMessage() == null || e.getMessage().isBlank())
                ? "요청하신 리소스를 찾을 수 없습니다."
                : e.getMessage();

        Api<Object> response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .resultMessage(message)
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Api<Object>> handleUnknown(Exception e) {

        log.error("Unhandled exception", e);

        Api<Object> response = Api.builder()
                .resultCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .resultMessage("일시적인 오류가 발생했습니다.")
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }




}
