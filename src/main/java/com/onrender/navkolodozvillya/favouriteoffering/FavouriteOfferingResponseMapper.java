package com.onrender.navkolodozvillya.favouriteoffering;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FavouriteOfferingResponseMapper implements Function<FavouriteOffering, FavouriteOfferingResponse> {
    @Override
    public FavouriteOfferingResponse apply(FavouriteOffering favouriteOffering) {
        return new FavouriteOfferingResponse(
                favouriteOffering.getId(),
                favouriteOffering.getUser().getId(),
                favouriteOffering.getOffering().getId());
    }
}
