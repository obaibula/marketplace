package com.onrender.navkolodozvillya.favouriteoffering;

public interface CustomFavouriteOfferingRepository<T> {
    FavouriteOfferingResponse customSave(String userEmail, Long offeringId);
}
