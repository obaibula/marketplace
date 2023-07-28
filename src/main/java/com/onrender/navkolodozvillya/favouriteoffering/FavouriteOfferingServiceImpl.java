package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.exception.entity.offering.OfferingIsAlreadyInFavoritesException;
import com.onrender.navkolodozvillya.exception.entity.offering.OfferingNotFoundException;
import com.onrender.navkolodozvillya.offering.OfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteOfferingServiceImpl implements FavouriteOfferingService {
    private final FavouriteOfferingRepository favouriteOfferingRepository;
    private final OfferingRepository offeringRepository;
    @Override
    public List<FavouriteOfferingResponse> findAllBy(Principal principal) {
        String userEmail = principal.getName();
        return favouriteOfferingRepository.findAllByUserEmail(userEmail);
    }

    // This optimization guarantees the avoidance of dirty checking
    // and the use of 3 queries instead of 4, as in the previous solution.
    // (see commit 6c4d4c12 on 27.07.2023 at 8:25)
    // Adding to favorites must be fast, so this method needs further optimizations in the future
    @Transactional
    public FavouriteOfferingResponse save(Long offeringId, Principal principal) {
        var userEmail = principal.getName();
        checkIfOfferingExists(offeringId);
        // All offerings must be unique for every user in their favorites
        checkIfAlreadyInFavourites(offeringId, userEmail);
        // todo: rename
        favouriteOfferingRepository.saveByUserEmailAndOfferingIdUsingNativeQuery(userEmail, offeringId);

        return favouriteOfferingRepository.findByUserEmailAndOfferingId(userEmail, offeringId);
    }

    private void checkIfOfferingExists(Long offeringId) {
        if(!offeringRepository.existsById(offeringId)){
            throw new OfferingNotFoundException("Offering not found with id - " + offeringId);
        }
    }

    private void checkIfAlreadyInFavourites(Long offeringId, String userEmail) {
        var favoriteOfferingExists =
                favouriteOfferingRepository.existsByUserEmailAndOfferingId(userEmail, offeringId);
        if (favoriteOfferingExists) {
            throw new OfferingIsAlreadyInFavoritesException("Offering is already in favourites.");
        }
    }
}
