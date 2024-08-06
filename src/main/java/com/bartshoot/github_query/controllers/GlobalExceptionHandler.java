package com.bartshoot.github_query.controllers;

import com.bartshoot.github_query.models.ApiError;
import com.bartshoot.github_query.services.GitHubApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GitHubApiException.class)
    public ResponseEntity<ApiError> handleError(GitHubApiException e) {
        return ResponseEntity.status(e.getErrorResponse().status()).body(
                new ApiError(e.getErrorResponse().status(), e.getErrorResponse().message()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> handleError(HandlerMethodValidationException e) {
        return ResponseEntity.status(400).body(new ApiError(400, e.getReason()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleError(Exception e) {
        return ResponseEntity.status(420).body(new ApiError(420, "Something went wrong"));
    }
}
