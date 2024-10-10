package com.team25.backend.exception;

import com.team25.backend.dto.response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(new ApiResponse<>(false, "유효성 검사 실패", errors),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ManagerException.class)
    public ResponseEntity<ApiResponse<String>> handleManagerException(ManagerException ex) {
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .status(false)
                .message(ex.getErrorCode().getMessage())
                .data(null)
                .build(),
            ex.getErrorCode().getHttpStatus()
        );
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ApiResponse<String>> handleReservationException(ReservationException ex) {
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .status(false)
                .message(ex.getErrorCode().getMessage())
                .data(null)
                .build(),
            ex.getErrorCode().getHttpStatus()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<String>apiResponse = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
}