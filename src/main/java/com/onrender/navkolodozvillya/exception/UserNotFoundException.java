package com.onrender.navkolodozvillya.exception;

public class UserNotFoundException extends EntityNotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
