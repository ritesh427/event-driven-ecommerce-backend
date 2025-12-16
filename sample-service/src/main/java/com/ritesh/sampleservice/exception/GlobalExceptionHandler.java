package com.ritesh.sampleservice.exception;

import com.ritesh.sampleservice.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* -------------------------
       SPRING BOOT 3.x HANDLER
       ------------------------- */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleMethodValidation(
            HandlerMethodValidationException ex) {

        String message = ex.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("VALIDATION_ERROR");
        response.setMessage(message);
        response.setTimestamp(Instant.now());
        response.setCorrelationId(MDC.get("correlationId"));

        return ResponseEntity.badRequest().body(response);
    }

    /* -------------------------
       REQUEST BODY VALIDATION
       ------------------------- */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBodyValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("VALIDATION_ERROR");
        response.setMessage(message);
        response.setTimestamp(Instant.now());
        response.setCorrelationId(MDC.get("correlationId"));

        return ResponseEntity.badRequest().body(response);
    }

    /* -------------------------
       QUERY / PATH VALIDATION
       ------------------------- */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {

        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("VALIDATION_ERROR");
        response.setMessage(message);
        response.setTimestamp(Instant.now());
        response.setCorrelationId(MDC.get("correlationId"));

        return ResponseEntity.badRequest().body(response);
    }

    /* -------------------------
       MISSING REQUEST PARAM
       ------------------------- */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("VALIDATION_ERROR");
        response.setMessage(ex.getParameterName() + " parameter is required");
        response.setTimestamp(Instant.now());
        response.setCorrelationId(MDC.get("correlationId"));

        return ResponseEntity.badRequest().body(response);
    }

    /* -------------------------
       ILLEGAL ARGUMENT
       ------------------------- */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("BAD_REQUEST");
        response.setMessage(ex.getMessage());
        response.setTimestamp(Instant.now());
        response.setCorrelationId(MDC.get("correlationId"));

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError("INTERNAL_SERVER_ERROR");
        response.setMessage("Unexpected error occurred");
        response.setTimestamp(Instant.now());
        response.setCorrelationId(MDC.get("correlationId"));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
