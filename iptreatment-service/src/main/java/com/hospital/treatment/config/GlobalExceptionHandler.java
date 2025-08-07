package com.hospital.treatment.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("details", request.getDescription(false));
        errorDetails.put("status", "error");
        errorDetails.put("service", "IPTreatment Service");
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("details", request.getDescription(false));
        errorDetails.put("status", "error");
        errorDetails.put("service", "IPTreatment Service");
        
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", "An unexpected error occurred in treatment service");
        errorDetails.put("details", request.getDescription(false));
        errorDetails.put("status", "error");
        errorDetails.put("service", "IPTreatment Service");
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}