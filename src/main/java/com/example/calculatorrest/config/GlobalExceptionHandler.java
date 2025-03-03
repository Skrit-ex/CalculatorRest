package com.example.calculatorrest.config;

import com.example.calculatorrest.exception.FilesNotFoundException;
import com.example.calculatorrest.exception.ResourceNotFoundException;
import com.example.calculatorrest.exception.UserRepositoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handlerResourcesNotFoundException(ResourceNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(FilesNotFoundException.class)
    public ResponseEntity<String> handlerFilesNotFoundException(FilesNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserRepositoryException.class)
    public ResponseEntity<String> handlerUserNotFoundException(UserRepositoryException ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalAccessError.class)
    public ResponseEntity<String> handlerIllegalAccessError(IllegalAccessError ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
