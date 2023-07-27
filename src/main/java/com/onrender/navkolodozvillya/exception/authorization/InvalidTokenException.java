package com.onrender.navkolodozvillya.exception.authorization;

public class InvalidTokenException extends AuthorizationException{
    public InvalidTokenException(String message) {
        super(message);
    }
}
