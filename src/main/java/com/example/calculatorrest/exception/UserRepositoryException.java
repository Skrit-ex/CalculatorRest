package com.example.calculatorrest.exception;

public class UserRepositoryException extends RuntimeException{

    public UserRepositoryException(String message){ super("User not found in repository");}
}
