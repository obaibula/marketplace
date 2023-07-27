package com.onrender.navkolodozvillya.exception.authorization;

public class MissingAuthorizationHeaderException extends AuthorizationException{
    public MissingAuthorizationHeaderException(String message) {
        super(message);
    }
}
