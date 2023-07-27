package com.onrender.navkolodozvillya.exception.entity.offering;

import com.onrender.navkolodozvillya.exception.entity.ResourceAlreadyExistsException;

public class OfferingIsAlreadyInFavoritesException extends ResourceAlreadyExistsException {
    public OfferingIsAlreadyInFavoritesException(String message) {
        super(message);
    }
}
