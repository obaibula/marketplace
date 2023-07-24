package com.onrender.navkolodozvillya.exception;

public class MissingAuthorizationHeaderException extends AuthorizationException{
    public MissingAuthorizationHeaderException(String message) {
        super(message);
    }
}
