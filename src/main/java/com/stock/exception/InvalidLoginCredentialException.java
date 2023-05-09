package com.stock.exception;

public class InvalidLoginCredentialException extends Exception {
    public InvalidLoginCredentialException(String message){
        super(message);
    }
}
