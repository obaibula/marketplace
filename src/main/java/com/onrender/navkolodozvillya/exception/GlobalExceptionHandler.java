package com.onrender.navkolodozvillya.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>>
    handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Map<String, List<String>>>
    handleValidationErrors(AuthorizationException e) {
        e.printStackTrace();
        var errors = List.of(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Map<String, List<String>>>
    handleEntityNotFoundException(EntityNotFoundException e){
        e.printStackTrace();
        var errors = List.of(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), NOT_FOUND);
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<Map<String, List<String>>>
    handleResourceAlreadyExistsException(ResourceAlreadyExistsException e){
        e.printStackTrace();
        var errors = List.of(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), CONFLICT);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>>
    handleGeneralExceptions(Exception e){
        e.printStackTrace();
        var errors = List.of(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>>
    handleRuntimeExceptions(RuntimeException e){
        e.printStackTrace();
        var errors = List.of(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
