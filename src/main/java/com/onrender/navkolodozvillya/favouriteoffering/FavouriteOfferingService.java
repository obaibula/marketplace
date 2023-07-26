package com.onrender.navkolodozvillya.favouriteoffering;

import java.security.Principal;
import java.util.List;

public interface FavouriteOfferingService {
    List<FavouriteOfferingResponse> findAllBy(Principal principal);

    FavouriteOfferingResponse save(Long offeringId, Principal principal);
}
