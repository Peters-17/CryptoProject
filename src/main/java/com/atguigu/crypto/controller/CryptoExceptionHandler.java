package com.atguigu.crypto.controller;

import com.atguigu.crypto.model.exception.InputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CryptoExceptionHandler {

    @ExceptionHandler(InputException.class)
    public ResponseEntity<String> handler(InputException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
