package com.onrender.navkolodozvillya.exception.entity.offering;

import com.onrender.navkolodozvillya.exception.entity.EntityNotFoundException;

public class OfferingNotFoundException extends EntityNotFoundException {
    public OfferingNotFoundException(String message) {
        super(message);
    }
}
