package com.onrender.navkolodozvillya.exception.entity.user;

import com.onrender.navkolodozvillya.exception.entity.ResourceAlreadyExistsException;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
