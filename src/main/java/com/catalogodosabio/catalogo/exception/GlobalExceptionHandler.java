package com.catalogodosabio.catalogo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Parâmetro inválido: " + ex.getName(),
                LocalDateTime.now()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro de servidor: " + ex.getMessage(),
                LocalDateTime.now()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}