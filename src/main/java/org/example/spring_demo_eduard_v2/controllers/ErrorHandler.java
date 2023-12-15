package org.example.spring_demo_eduard_v2.controllers;

import org.example.spring_demo_eduard_v2.dto.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDto> handlerException(DataIntegrityViolationException e, WebRequest webRequest){
        return ResponseEntity.status(500)
                .body(ErrorDto.builder()
                        .statusCode(500)
                        .messages(List.of(e.getRootCause().getMessage()))
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handlerException(MethodArgumentNotValidException e, WebRequest webRequest){
        return ResponseEntity
                .status(400)
                .body(ErrorDto.builder()
                        .statusCode(400)
                        .messages(e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList())
                        .build());
    }
}
