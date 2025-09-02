package com.LocadoraFilmes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<?> tratarValidacao(MethodArgumentNotValidException ex) {
        StringBuilder erros = new StringBuilder("Erros de validação: ");
        ex.getBindingResult().getFieldErrors().forEach(err -> 
            erros.append(err.getField()).append(" - ").append(err.getDefaultMessage()).append("; ")
        );
            return ResponseEntity.badRequest().body(erros.toString());
    }
}
