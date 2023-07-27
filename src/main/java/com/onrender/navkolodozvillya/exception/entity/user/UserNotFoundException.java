package com.onrender.navkolodozvillya.exception.entity.user;

import com.onrender.navkolodozvillya.exception.entity.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
