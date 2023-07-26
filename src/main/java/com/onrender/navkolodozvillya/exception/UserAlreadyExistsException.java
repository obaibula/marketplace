package com.onrender.navkolodozvillya.exception;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
