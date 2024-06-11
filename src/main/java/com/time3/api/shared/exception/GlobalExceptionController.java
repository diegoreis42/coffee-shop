package com.time3.api.shared.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorHandling> handleUserException(GlobalException exception, HttpServletRequest request) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(
                        new ErrorHandling(LocalDateTime.now(), exception.getHttpStatus().value(),
                                request.getServletPath(), request.getMethod(),
                                exception.getMessage()));
    }
}
