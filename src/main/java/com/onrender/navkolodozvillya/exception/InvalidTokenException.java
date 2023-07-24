package com.onrender.navkolodozvillya.exception;

public class InvalidTokenException extends AuthorizationException{
    public InvalidTokenException(String message) {
        super(message);
    }
}
