package com.example.calculatorrest.exception;

public class FilesNotFoundException extends RuntimeException{

    public FilesNotFoundException(String message){
        super("File not found in dataBase Operation or other error");
    }
}
